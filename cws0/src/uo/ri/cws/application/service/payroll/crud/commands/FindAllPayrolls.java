package uo.ri.cws.application.service.payroll.crud.commands;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryDto;
import uo.ri.cws.application.service.payroll.crud.PayrollAssembler;
import uo.ri.util.exception.BusinessException;

public class FindAllPayrolls implements Command<List<PayrollSummaryDto>> {

    
    private PayrollGateway payrollGateway = Factories.persistence.forPayroll();


    @Override
    public List<PayrollSummaryDto> execute() throws BusinessException {
        return payrollGateway.findAll().stream().map(PayrollAssembler::toSummarizedDto).toList();
    }
}
