package uo.ri.cws.application.service.payroll.crud.commands;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollRecord;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollDto;
import uo.ri.cws.application.service.payroll.crud.PayrollAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.math.Rounds;

public class GeneratePayrolls implements Command<List<PayrollDto>> {

	private final LocalDate presentDate;

	private final ContractGateway contractGateway = Factories.persistence
			.forContract();
	private final PayrollGateway payrollGateway = Factories.persistence
			.forPayroll();
	private final WorkOrderGateway workOrderGateway = Factories.persistence
			.forWorkOrder();

	public GeneratePayrolls() {
		this.presentDate = LocalDate.now();
	}

	public GeneratePayrolls(LocalDate present) {
		ArgumentChecks.isNotNull(present,
				"La fecha de referencia no puede ser nula");
		this.presentDate = present;
	}

	@Override
	public List<PayrollDto> execute() throws BusinessException {

		List<PayrollDto> generatedPayrolls = new ArrayList<>();
		LocalDate inicioMesAnterior = presentDate.minusMonths(1)
				.with(TemporalAdjusters.firstDayOfMonth());
		LocalDate finMesAnterior = presentDate.minusMonths(1)
				.with(TemporalAdjusters.lastDayOfMonth());

		List<ContractRecord> activeContracts = contractGateway
				.findContractBetween(inicioMesAnterior, finMesAnterior);

		for (ContractRecord contract : activeContracts) {

			if (payrollGateway.existsPayrollForContractInDate(contract.id,
					finMesAnterior)) {
				continue;
			}

			ProfessionalGroupRecord pg = Factories.persistence
					.forProfessionalGroup()
					.findById(contract.professionalGroupId).get();

			double baseSalary = Rounds
					.toCents(contract.annualBaseSalary / 14.0);

			double extraSalary = 0.0;
			if (finMesAnterior.getMonthValue() == 6
					|| finMesAnterior.getMonthValue() == 12) {
				extraSalary = baseSalary;
			}

			double trienniumEarning = calcularTrienios(contract,
					finMesAnterior, pg);

			double productivityEarning = calcularProductividad(
					contract.mechanicId, inicioMesAnterior, finMesAnterior, pg);

			double grossSalary = baseSalary + extraSalary + trienniumEarning
					+ productivityEarning;

			double taxDeduction = Rounds
					.toCents(grossSalary * contract.taxRate);
			double nicDeduction = Rounds
					.toCents(contract.annualBaseSalary * 0.05 / 12.0);

			PayrollRecord record = new PayrollRecord();
			record.id = UUID.randomUUID().toString();
			record.version = 1L;
			record.contractId = contract.id;
			record.date = finMesAnterior;
			record.baseSalary = baseSalary;
			record.extraSalary = extraSalary;
			record.trienniumEarning = trienniumEarning;
			record.productivityEarning = productivityEarning;
			record.taxDeduction = taxDeduction;
			record.nicDeduction = nicDeduction;
			payrollGateway.add(record);

			generatedPayrolls.add(PayrollAssembler.toDto(record));
		}

		return generatedPayrolls;
	}

	/**
	 * Lógica para calcular la productividad del mecánico en función de las
	 * órdenes de trabajo cerradas (workOrders facturadas o listas) en el
	 * mes anterior.
	 */
	private double calcularProductividad(String mechanicId, LocalDate start,
			LocalDate end, ProfessionalGroupRecord pg) {

		double totalInterventionsValue = workOrderGateway
				.findWorkOrdersByMechanicIdInDate(mechanicId,
						start, end);
		return Rounds.toCents(totalInterventionsValue * pg.productivityRate);
	}

	/**
	 * Lógica para calcular el plus por trienios.
	 */
	private double calcularTrienios(ContractRecord contract,
			LocalDate finMesAnterior, ProfessionalGroupRecord pg) {

		int years = java.time.Period.between(contract.startDate, finMesAnterior)
				.getYears();
		int trienios = years / 3;

		double valorTrienioPorGrupo = pg.trienniumPayment;
		return Rounds.toCents(trienios * valorTrienioPorGrupo);
	}
}