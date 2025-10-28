package uo.ri.cws.application.ui.manager.contracts.contract.action;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.contract.ContractCrudService;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractSummaryDto;
import uo.ri.cws.application.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class ListContractsOfMechanicAction implements Action {

    private final ContractCrudService contract_service = Factories.service
        .forContractCrudService();

    @Override
    public void execute() throws BusinessException {
        String nif = Console.readString("Mechanic nif");
        List<ContractSummaryDto> list_of_contracts = contract_service
            .findByMechanicNif(nif);
        for (ContractSummaryDto c : list_of_contracts) {
            Printer.printContractSummary(c);
        }
    }
}
