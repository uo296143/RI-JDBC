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

public class FindPayrollByProfessionalGroup
		implements Command<List<PayrollSummaryDto>> {

	private PayrollGateway payrollGateway = Factories.persistence.forPayroll();
	private String name;

	public FindPayrollByProfessionalGroup(String name) {
		ArgumentChecks.isNotEmpty(name);
		ArgumentChecks.isNotBlank(name);
		this.name = name;
	}

	@Override
	public List<PayrollSummaryDto> execute() throws BusinessException {
		BusinessChecks.exists(
				Factories.persistence.forProfessionalGroup().findByName(name),
				"No se pueden listar las nóminas del grupo profesional ya que este no existe");
		
		return payrollGateway.findPayrollsByProfessionalGroup(name).stream()
				.map(PayrollAssembler::toSummarizedDto).toList();
	}

}
