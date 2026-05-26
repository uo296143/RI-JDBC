package uo.ri.cws.application.service.payroll.crud.commands;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryDto;
import uo.ri.cws.application.service.payroll.crud.PayrollAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class FindPayrollsByMechanicId
		implements Command<List<PayrollSummaryDto>> {

	private String mechanicId;
	private PayrollGateway payrollGateway = Factories.persistence.forPayroll();

	public FindPayrollsByMechanicId(String mechanicId) {
		ArgumentChecks.isNotEmpty(mechanicId);
		ArgumentChecks.isNotBlank(mechanicId);
		this.mechanicId = mechanicId;
	}

	@Override
	public List<PayrollSummaryDto> execute() throws BusinessException {
		
		BusinessChecks.exists(
				Factories.persistence.forMechanic().findById(mechanicId),
				"No se pueden listar las nóminas del mecánico ya que el mecánico no existe");

		return payrollGateway.findPayrollsByMechanicId(mechanicId).stream()
				.map(PayrollAssembler::toSummarizedDto).toList();

	}

}
