package uo.ri.cws.application.ui.manager.contracts.contract.action;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.contract.ContractCrudService;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractDto;
import uo.ri.cws.application.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class ShowContractDetailsAction implements Action {

    private final ContractCrudService contract_service = Factories.service
        .forContractCrudService();

    @Override
    public void execute() throws BusinessException {
        String id = Console.readString("Contract id");
        Optional<ContractDto> c = contract_service.findById(id);
        Printer.printContractDetails(c.get());
    }
}