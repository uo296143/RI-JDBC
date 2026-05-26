package uo.ri.cws.application.service.payroll.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollDto;
import uo.ri.cws.application.service.payroll.crud.PayrollAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindPayrollById implements Command<Optional<PayrollDto>> {

	private String id;
    
    public FindPayrollById(String id) {
        ArgumentChecks.isNotEmpty(id);
        ArgumentChecks.isNotEmpty(id);
        this.id = id;
    }

    @Override
    public Optional<PayrollDto> execute() throws BusinessException {
        
    	Optional<PayrollRecord> optionalPayroll = Factories.persistence.forPayroll().findById(id);
    	return optionalPayroll.map(PayrollAssembler::toDto);
    	
    }

}
