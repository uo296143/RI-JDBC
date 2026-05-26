package uo.ri.cws.application.ui.manager.mechanic.action;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.contract.ContractCrudService;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractDto;
import uo.ri.cws.application.ui.util.Printer;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

/**
 * Action that list all the mechanics on the system
 */
public class ListAllMechanicsWithContractInForceAction implements Action {

	/* Service used to perform mechanic CRUD operations. */
	private final ContractCrudService service = Factories.service
			.forContractCrudService();

	/**
	 * List all the mechanics of the system
	 *
	 * @throws BusinessException if the service layer reports a business error
	 */
	@Override
	public void execute() throws BusinessException {
		List<ContractDto> contracts = service.findInforceContracts();
		contracts.stream()
				.forEach(contract -> Printer.printSumarizedContract(contract));
	}
}
