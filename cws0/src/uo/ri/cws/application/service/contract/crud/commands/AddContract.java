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
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.math.Rounds;

public class AddContract implements Command<ContractDto> {

	private final ContractDto contractDto;
	private final LocalDate referenceDate;

	private final ContractTypeGateway contractTypeGateway = Factories.persistence.forContractType();
	private final MechanicGateway mechanicGateway = Factories.persistence.forMechanic();
	private final ProfessionalGroupGateway professionalGateway = Factories.persistence.forProfessionalGroup();
	private final ContractGateway contractGateway = Factories.persistence.forContract();
	private final PayrollGateway payrollGateway = Factories.persistence.forPayroll();

	private static final double[] SEGMENTS = { 12450, 20200, 35200, 60000, 300000 };
	private static final double[] RATES = { 0.19, 0.24, 0.30, 0.37, 0.45, 0.47 };

	public AddContract(ContractDto contract) {
		this(contract, LocalDate.now());
	}

	public AddContract(ContractDto contract, LocalDate referenceDate) {
		ArgumentChecks.isNotNull(contract);
		ArgumentChecks.isNotBlank(contract.mechanic.nif);
		ArgumentChecks.isNotBlank(contract.contractType.name);
		ArgumentChecks.isNotBlank(contract.professionalGroup.name);
		ArgumentChecks.isNotNull(contract.annualBaseSalary);
		ArgumentChecks.isTrue(contract.annualBaseSalary > 0);
		if ("FIXED_TERM".equals(contract.contractType.name)) {
			ArgumentChecks.isNotNull(contract.endDate, "Un contrato temporal debe tener fecha de fin");
		}
		this.contractDto = contract;
		this.referenceDate = referenceDate;
	}

	@Override
	public ContractDto execute() throws BusinessException {
		
		Optional<MechanicRecord> mechanicOpt = mechanicGateway.findByNif(contractDto.mechanic.nif);
		BusinessChecks.exists(mechanicOpt, "The mechanic doesn't exist");
		MechanicRecord mechanic = mechanicOpt.get();

		Optional<ContractTypeRecord> typeOpt = contractTypeGateway.findByName(contractDto.contractType.name);
		BusinessChecks.exists(typeOpt, "The contract type doesn't exist");

		Optional<ProfessionalGroupRecord> groupOpt = professionalGateway.findByName(contractDto.professionalGroup.name);
		BusinessChecks.exists(groupOpt, "The professional group doesn't exist");

		LocalDate baseDate = (contractDto.startDate == null) ? referenceDate : contractDto.startDate;
		LocalDate startDateNextMonth = baseDate.plusMonths(1).withDayOfMonth(1);

		if (contractDto.endDate != null && contractDto.endDate.isBefore(startDateNextMonth)) {
			throw new BusinessException("End date can't be earlier than start date");
		}
		
		Optional<ContractRecord> activeContractOpt = contractGateway.findInForceContractByMechanicId(mechanic.id);
		
		if (activeContractOpt.isPresent()) {
			ContractRecord oldContract = activeContractOpt.get();
			LocalDate endOfCurrentMonth = referenceDate.with(TemporalAdjusters.lastDayOfMonth());
						
			oldContract.endDate = endOfCurrentMonth;
			oldContract.state = "TERMINATED";
			contractGateway.update(oldContract);
						
			long totalDays = java.time.temporal.ChronoUnit.DAYS.between(oldContract.startDate, endOfCurrentMonth);
			if (totalDays >= 365) {
				calcularIndemnizacion(oldContract, typeOpt.get(), endOfCurrentMonth);
			}
		}
	
		ContractRecord newContract = new ContractRecord();
		newContract.id = UUID.randomUUID().toString();
		newContract.version = 1L;
		newContract.mechanicId = mechanic.id;
		newContract.contractTypeId = typeOpt.get().id;
		newContract.professionalGroupId = groupOpt.get().id;
		newContract.annualBaseSalary = contractDto.annualBaseSalary;
		newContract.startDate = startDateNextMonth;
		newContract.endDate = contractDto.endDate;
		newContract.taxRate = forSalary(contractDto.annualBaseSalary);
		newContract.state = "IN_FORCE";
		newContract.settlement = 0.0;

		contractGateway.add(newContract);
		
		contractDto.id = newContract.id;
		contractDto.version = newContract.version;
		contractDto.startDate = newContract.startDate;
		contractDto.taxRate = newContract.taxRate;
		contractDto.state = newContract.state;
		contractDto.settlement = newContract.settlement;
		contractDto.mechanic.id = mechanic.id;
		contractDto.contractType.id = typeOpt.get().id;
		contractDto.professionalGroup.id = groupOpt.get().id;

		return contractDto;
	}

	/**
	 * Calcula la indemnización del contrato extinguido basándose en las últimas 12 nóminas
	 */
	private void calcularIndemnizacion(ContractRecord oldContract, ContractTypeRecord type, LocalDate endDate) {
		LocalDate startDate = endDate.minusMonths(12).withDayOfMonth(1);
		double totalGrossSalaryLastYear = payrollGateway.grossSalaryOfTheLastYear(oldContract.id, startDate, endDate);
		double dailyMeanGrossSalary = totalGrossSalaryLastYear / 365.0;
		
		int yearsWorked = Period.between(oldContract.startDate, endDate).getYears();
		
		double settlement = dailyMeanGrossSalary * type.compensationDaysPerYear * yearsWorked;
		
		oldContract.settlement = Rounds.toCents(settlement);
		contractGateway.update(oldContract);
	}

	/**
	 * Algoritmo por tramos para calcular la tasa de IRPF en base al salario bruto anual
	 */
	private double forSalary(double annualSalary) {
		for (int i = 0; i < SEGMENTS.length; i++) {
			if (annualSalary < SEGMENTS[i]) {
				return RATES[i];
			}
		}
		return RATES[RATES.length - 1];
	}
}