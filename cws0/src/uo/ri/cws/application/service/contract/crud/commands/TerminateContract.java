package uo.ri.cws.application.service.contract.crud.commands;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.TemporalAdjusters;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway.ContractTypeRecord;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class TerminateContract implements Command<Void> {

    private ContractTypeGateway persistence_contractType = Factories.persistence
        .forContractType();
    private ContractGateway persistence_contract = Factories.persistence
        .forContract();
    private PayrollGateway persistence_payroll = Factories.persistence
        .forPayroll();

    private String id;

    public TerminateContract(String id) {
        ArgumentChecks.isNotBlank(id, "El id del contrato is blank");
        ArgumentChecks.isNotEmpty(id, "El id del contrato es null o empty");
        this.id = id;
    }

    /*
     * BusinessException when, - The contract does not exist, or - the contract
     * is not in force, or
     */
    @Override
    public Void execute() throws BusinessException {
        Optional<ContractRecord> optional_contract_record = persistence_contract
            .findById(id);
        BusinessChecks.exists(optional_contract_record);
        if (!optional_contract_record.get().state.equals("IN_FORCE"))
            throw new BusinessException("The contract wasn´t in force.");

        LocalDate ultimoDiaDelMes = LocalDate.now()
            .with(TemporalAdjusters.lastDayOfMonth());
        persistence_contract.finishContract(id, ultimoDiaDelMes);
        if (llevaMasDeUnAnoTrabajando())
            calcularIndemnizacion();
        return null;
    }

    private boolean llevaMasDeUnAnoTrabajando() {

        Optional<ContractRecord> contract_record = persistence_contract
            .findById(id);
        return Period
            .between(contract_record.get().startDate,
                    LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()))
            .getYears() > 1;
    }

    private void calcularIndemnizacion() {

        Optional<ContractRecord> contract_record = persistence_contract
            .findById(id);
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

}
