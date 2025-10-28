package uo.ri.cws.application.service.contract.crud.commands;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.TemporalAdjusters;
import java.util.Optional;
import java.util.UUID;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway.ContractTypeRecord;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractDto;
import uo.ri.cws.application.service.contract.crud.ContractAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class AddContract implements Command<ContractDto> {

    private ContractRecord contract_record;
    private ContractDto contract_dto;
    private Optional<MechanicRecord> mechanic_optional;
    private Optional<ContractTypeRecord> contractType_record;
    private Optional<ProfessionalGroupRecord> professionalGroup_optional;

    private ContractTypeGateway persistence_contractType = Factories.persistence
        .forContractType();
    private MechanicGateway persistence_mechanic = Factories.persistence
        .forMechanic();
    private ProfessionalGroupGateway persistence_professional = Factories.persistence
        .forProfessionalGroup();
    private ContractGateway persistence_contract = Factories.persistence
        .forContract();
    private PayrollGateway persistence_payroll = Factories.persistence
        .forPayroll();

    // Para calcular el porcentaje de IRPF según el salario.
    private static double segments[] = { 12450, 20200, 35200, 60000, 300000 };
    private static double rates[] = { 0.19, 0.24, 0.30, 0.37, 0.45, 0.47 };

    public AddContract(ContractDto contract) throws BusinessException {

        ArgumentChecks.isNotNull(contract);
        ArgumentChecks.isNotBlank(contract.mechanic.nif);
        ArgumentChecks.isNotBlank(contract.contractType.name);
        ArgumentChecks.isNotBlank(contract.professionalGroup.name);
        ArgumentChecks.isNotNull(contract.annualBaseSalary);
        ArgumentChecks.isTrue(contract.annualBaseSalary > 0);
        if (contract.contractType.name.equals("FIXED_TERM")) {
            ArgumentChecks.isTrue(contract.endDate != null);
        }

        this.contract_dto = contract;
        contract_record = new ContractRecord();
    }

    /*
     * 1 - Se comprueba si ya hay un contrato en vigor, si es el caso se
     * rescinde con fecha de finalización el último día del mes y se calcula la
     * indemnización.
     */
    @Override
    public ContractDto execute() throws BusinessException {

        /*
         * A apartir del nombre / nif se obtiene el grupo profesional, el tipo
         * de contrato y el mecánico
         */
        contractType_record = persistence_contractType
            .findByName(contract_dto.contractType.name);
        mechanic_optional = persistence_mechanic
            .findByNif(contract_dto.mechanic.nif);
        professionalGroup_optional = persistence_professional
            .findByName(contract_dto.professionalGroup.name);

        BusinessChecks.exists(mechanic_optional, "The mechanic doesn´t exist");
        BusinessChecks.exists(contractType_record,
                "The contract type doesn´t exist");
        BusinessChecks.exists(professionalGroup_optional,
                "The professional group doesn´t exist");

        contract_record.id = UUID.randomUUID().toString();
        contract_record.version = 1L;
        contract_record.mechanicId = mechanic_optional.get().id;
        contract_record.contractTypeId = contractType_record.get().id;
        contract_record.professionalGroupId = professionalGroup_optional
            .get().id;
        contract_record.annualBaseSalary = contract_dto.annualBaseSalary;

        /*
         * La fecha de inicio del contrato es el 1º día del mes siguiente.
         * 
         * Hago esta comprobacion por que por pantalla no se pide fecha de
         * inicio entonces pongo la actual pero en las pruebas igual se quiere
         * poner una fecha de inicio posterior.
         */
        LocalDate primerDiaMesSiguiente;
        if (contract_dto.startDate == null) {
            primerDiaMesSiguiente = LocalDate.now()
                .plusMonths(1)
                .withDayOfMonth(1);
        } else {
            primerDiaMesSiguiente = contract_dto.startDate.plusMonths(1)
                .withDayOfMonth(1);
        }

        contract_record.startDate = primerDiaMesSiguiente;

        // Los impuestos se calculan en funcion del salario para que pasen los
        // tests.
        contract_record.taxRate = forSalary(contract_dto.annualBaseSalary);
        contract_record.settlement = contract_dto.settlement;
        contract_record.state = "IN_FORCE";

        if (contract_dto.endDate != null
                && contract_dto.endDate.isBefore(contract_record.startDate)) {
            throw new BusinessException(
                    "End date can´t be earlier than star date");
        }

        if (yaHayUnContratoEnVigorParaElMecanico()) {
            finalizarContrato();
            if (llevaMasDeUnAnoTrabajando())
                calcularIndemnizacion();
        }

        persistence_contract.add(contract_record);
        Console.println("Contract created");
        ContractDto mr;
        mr = contract_dto;
        mr = ContractAssembler
            .toDto(persistence_contract.findById(contract_record.id).get());
        mr.contractType = ContractAssembler
            .toContractTypeOfContractDto(persistence_contractType
                .findById(contract_record.contractTypeId)
                .get());
        mr.mechanic = ContractAssembler.toMechanicOfContractDto(
                persistence_mechanic.findById(contract_record.mechanicId)
                    .get());

        mr.professionalGroup = ContractAssembler
            .toProfessionalGroupOfContractDto(persistence_professional
                .findById(contract_record.professionalGroupId)
                .get());
        return mr;
    }

    private boolean llevaMasDeUnAnoTrabajando() {
        Optional<ContractRecord> contract_record;

        if (contract_dto.mechanic.id != null) {
            contract_record = persistence_contract
                .findByMechanicId(contract_dto.mechanic.id);
        } else {
            // No se puede identificar al mecanico por id y ha que hacerlo por
            // el nif.
            Optional<MechanicRecord> mechanic_record = persistence_mechanic
                .findByNif(contract_dto.mechanic.nif);
            contract_record = persistence_contract
                .findByMechanicId(mechanic_record.get().id);
        }
        return Period
            .between(contract_record.get().startDate,
                    LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()))
            .getYears() > 1;
    }

    private void finalizarContrato() {
        Optional<ContractRecord> contract_record;
        String oldContractId;
        if (contract_dto.mechanic.id != null) {
            contract_record = persistence_contract
                .findByMechanicId(contract_dto.mechanic.id);
            oldContractId = contract_record.get().id;
        } else {
            // No se puede identificar al mecanico por id y ha que hacerlo por
            // el nif.
            Optional<MechanicRecord> mechanic_record = persistence_mechanic
                .findByNif(contract_dto.mechanic.nif);
            contract_record = persistence_contract
                .findByMechanicId(mechanic_record.get().id);
            oldContractId = contract_record.get().id;
        }

        persistence_contract.finishContract(oldContractId,
                LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()));
    }

    private boolean yaHayUnContratoEnVigorParaElMecanico() {

        Optional<ContractRecord> contract_record;

        if (contract_dto.mechanic.id != null) {
            contract_record = persistence_contract
                .findByMechanicId(contract_dto.mechanic.id);
        } else {
            // No se puede identificar al mecanico por id y ha que hacerlo por
            // el nif.
            Optional<MechanicRecord> mechanic_record = persistence_mechanic
                .findByNif(contract_dto.mechanic.nif);
            contract_record = persistence_contract
                .findByMechanicId(mechanic_record.get().id);
        }

        return contract_record.isPresent();

    }

    private void calcularIndemnizacion() {
        Optional<ContractRecord> contract_record;

        if (contract_dto.mechanic.id != null) {
            contract_record = persistence_contract
                .findByMechanicId(contract_dto.mechanic.id);
        } else {
            // No se puede identificar al mecanico por id y ha que hacerlo por
            // el nif.
            Optional<MechanicRecord> mechanic_record = persistence_mechanic
                .findByNif(contract_dto.mechanic.nif);
            contract_record = persistence_contract
                .findByMechanicId(mechanic_record.get().id);
        }

        String oldContractId = contract_record.get().id;
        double total_gross_salary = persistence_payroll
            .grossSalaryOfTheLastYear(oldContractId);
        String contractTypeId = contract_record.get().contractTypeId;
        Optional<ContractTypeRecord> contractTypeOfOldContract = persistence_contractType
            .findById(contractTypeId);
        int years_worked = Period
            .between(contract_record.get().startDate,
                    LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()))
            .getYears();
        double settlement = total_gross_salary / 365
                * contractTypeOfOldContract.get().compensationDaysPerYear
                * years_worked;

        persistence_contract.insertSettlement(settlement, oldContractId);
    }

    private double forSalary(double annualSalary) {
        for (int i = 0; i < segments.length; i++) {
            if (annualSalary < segments[i]) {
                return rates[i];
            }
        }
        return rates[rates.length - 1];
    }

}
