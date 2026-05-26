package uo.ri.cws.application.service.payroll.crud.commands;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteLastGeneratedOfMechanicId implements Command<Void> {

    private PayrollGateway payrollGateway = Factories.persistence.forPayroll();
    private String mechanicId;
    
    public DeleteLastGeneratedOfMechanicId(String mechanicId) {
    	ArgumentChecks.isNotBlank(mechanicId);
    	ArgumentChecks.isNotEmpty(mechanicId);
    	this.mechanicId = mechanicId;
    }

    @Override
    public Void execute() throws BusinessException {
    	
    	LocalDate finMesAnterior = LocalDate.now().minusMonths(1)
				.with(TemporalAdjusters.lastDayOfMonth());
    	
    	return payrollGateway.deleteLastPayrollOfMechanicId(mechanicId, finMesAnterior);
    	
    }

}
