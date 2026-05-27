package uo.ri.cws.application.ui.manager.payroll.action;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollDto;
import uo.ri.cws.application.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class ShowPayrollAction implements Action {

    @Override
    public void execute() throws BusinessException {
        String payrollId = Console.readString("Payroll id");
        Optional<PayrollDto> optionalDto = Factories.service.forPayrollService().findById(payrollId);
        if(optionalDto.isEmpty()) {
        	Console.print("La nómina no existe");
        }else {
        	Printer.printPayrollDetails(optionalDto.get());
        }
        
    }
}