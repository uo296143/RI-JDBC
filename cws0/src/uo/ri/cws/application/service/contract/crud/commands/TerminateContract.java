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

	private ContractTypeGateway persistenceContractType = Factories.persistence
			.forContractType();
	private ContractGateway persistenceContract = Factories.persistence
			.forContract();
	private PayrollGateway persistencePayroll = Factories.persistence
			.forPayroll();

	private String id;

	public TerminateContract(String id) {
		ArgumentChecks.isNotBlank(id, "El id del contrato is blank");
		ArgumentChecks.isNotEmpty(id, "El id del contrato es null o empty");
		this.id = id;
	}

	@Override
	public Void execute() throws BusinessException {
		Optional<ContractRecord> optionalContractRecord = persistenceContract
				.findById(id);
		BusinessChecks.exists(optionalContractRecord);

		ContractRecord contract = optionalContractRecord.get();

		if (!"IN_FORCE".equals(contract.state)) {
			throw new BusinessException("The contract wasn´t in force.");
		}

		LocalDate ultimoDiaDelMes = LocalDate.now()
				.with(TemporalAdjusters.lastDayOfMonth());
		contract.endDate = ultimoDiaDelMes;
		contract.state = "TERMINATED";
		persistenceContract.update(contract);

		if (llevaMasDeUnAnoTrabajando(contract, ultimoDiaDelMes)) {
			calcularIndemnizacion(contract, ultimoDiaDelMes);
		}

		return null;
	}

	private boolean llevaMasDeUnAnoTrabajando(ContractRecord contract,
			LocalDate ultimoDiaDelMes) {
		return Period.between(contract.startDate, ultimoDiaDelMes)
				.getYears() > 1;
	}

	private void calcularIndemnizacion(ContractRecord contract,
			LocalDate ultimoDiaDelMes) {
		LocalDate inicioRango = ultimoDiaDelMes.minusMonths(12).withDayOfMonth(1);
		double totalGrossSalary = persistencePayroll
				.grossSalaryOfTheLastYear(contract.id, inicioRango, ultimoDiaDelMes);

		Optional<ContractTypeRecord> contractTypeOfOldContract = persistenceContractType
				.findById(contract.contractTypeId);

		int yearsWorked = Period.between(contract.startDate, ultimoDiaDelMes)
				.getYears();

		double settlement = (totalGrossSalary / 365.0)
				* contractTypeOfOldContract.get().compensationDaysPerYear
				* yearsWorked;

		contract.settlement = settlement;
		persistenceContract.update(contract);
	}
}