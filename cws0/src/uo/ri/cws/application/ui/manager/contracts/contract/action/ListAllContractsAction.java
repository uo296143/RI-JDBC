package uo.ri.cws.application.ui.manager.contracts.contract.action;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.contract.ContractCrudService;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractSummaryDto;
import uo.ri.cws.application.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class ListAllContractsAction implements Action {

    private final ContractCrudService contract_service = Factories.service
        .forContractCrudService();

    @Override
    public void execute() throws Exception {
        List<ContractSummaryDto> contracts = contract_service.findAll();
        if (contracts.isEmpty()) {
            Console.println("No contracts found");
            return;
        }
        for (ContractSummaryDto c : contracts) {
            Printer.printContractSummary(c);
        }
    }

}