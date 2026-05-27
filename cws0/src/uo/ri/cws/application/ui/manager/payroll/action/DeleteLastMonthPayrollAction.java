package uo.ri.cws.application.ui.manager.payroll.action;

import uo.ri.conf.Factories;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class DeleteLastMonthPayrollAction implements Action {

    @Override
    public void execute() throws BusinessException {
        
    	Factories.service.forPayrollService().deleteLastGenerated();
        Console.println("Last month's payrolls deleted");
    }
}