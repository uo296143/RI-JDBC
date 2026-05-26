package uo.ri.cws.application.service.payroll.crud.commands;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.util.exception.BusinessException;

public class DeleteLastGenerated implements Command<Integer> {

    private PayrollGateway payrollGateway = Factories.persistence.forPayroll();

    @Override
    public Integer execute() throws BusinessException {
    	
		LocalDate finMesAnterior = LocalDate.now().minusMonths(1)
				.with(TemporalAdjusters.lastDayOfMonth());

    	return payrollGateway.deletePayrollsOf(finMesAnterior);
    	
    }

}
