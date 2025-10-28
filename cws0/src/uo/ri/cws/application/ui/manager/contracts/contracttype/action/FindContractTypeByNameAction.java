package uo.ri.cws.application.ui.manager.contracts.contracttype.action;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService.ContractTypeDto;
import uo.ri.cws.application.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class FindContractTypeByNameAction implements Action {

    private ContractTypeCrudService contractType_service = Factories.service
        .forContractTypeCrudService();

    @Override
    public void execute() throws BusinessException {
        String name = Console.readString("Contract type name");
        Optional<ContractTypeDto> dto = contractType_service.findByName(name);
        if (dto.isEmpty()) {
            Console.print("The contract type doesn´t exist");
        } else {
            Printer.printContractType(dto.get());

        }
    }

}