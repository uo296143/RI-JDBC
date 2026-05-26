package uo.ri.cws.application.service.payroll.crud;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.util.command.CommandExecutor;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractDto;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractSummaryDto;
import uo.ri.cws.application.service.contract.crud.commands.AddContract;
import uo.ri.cws.application.service.contract.crud.commands.DeleteContract;
import uo.ri.cws.application.service.contract.crud.commands.ListAllContracts;
import uo.ri.cws.application.service.contract.crud.commands.ListContractById;
import uo.ri.cws.application.service.contract.crud.commands.ListContractsByMechanicNif;
import uo.ri.cws.application.service.contract.crud.commands.ListInForceContracts;
import uo.ri.cws.application.service.contract.crud.commands.TerminateContract;
import uo.ri.cws.application.service.contract.crud.commands.UpdateContract;
import uo.ri.cws.application.service.payroll.PayrollService;
import uo.ri.cws.application.service.payroll.crud.commands.DeleteLastGenerated;
import uo.ri.cws.application.service.payroll.crud.commands.DeleteLastGeneratedOfMechanicId;
import uo.ri.cws.application.service.payroll.crud.commands.GeneratePayrolls;
import uo.ri.cws.application.service.payroll.crud.commands.*;
import uo.ri.util.exception.BusinessException;

public class PayrollServiceImpl implements PayrollService {
	
	CommandExecutor c = new CommandExecutor();

	@Override
	public List<PayrollDto> generateForPreviousMonth()
			throws BusinessException {
		return c.execute(new GeneratePayrolls());
	}

	@Override
	public List<PayrollDto> generateForPreviousMonthOf(LocalDate present)
			throws BusinessException {
		return c.execute(new GeneratePayrolls(present));
	}

	@Override
	public void deleteLastGeneratedOfMechanicId(String mechanicId)
			throws BusinessException {
		c.execute(new DeleteLastGeneratedOfMechanicId(mechanicId));
		
	}

	@Override
	public int deleteLastGenerated() throws BusinessException {
		return c.execute(new DeleteLastGenerated());
	}

	@Override
	public Optional<PayrollDto> findById(String id) throws BusinessException {
		return c.execute(new FindPayrollById(id));
	}

	@Override
	public List<PayrollSummaryDto> findAllSummarized()
			throws BusinessException {
		return c.execute(new FindAllPayrolls());
	}

	@Override
	public List<PayrollSummaryDto> findSummarizedByMechanicId(String id)
			throws BusinessException {
		return c.execute(new FindPayrollsByMechanicId(id));
	}

	@Override
	public List<PayrollSummaryDto> findSummarizedByProfessionalGroupName(
			String name) throws BusinessException {
		return c.execute(new FindPayrollByProfessionalGroup(name));
	}

   

}
