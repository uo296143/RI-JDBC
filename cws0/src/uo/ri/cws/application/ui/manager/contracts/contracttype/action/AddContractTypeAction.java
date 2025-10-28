package uo.ri.cws.application.ui.manager.contracts.contracttype.action;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService.ContractTypeDto;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class AddContractTypeAction implements Action {

    private ContractTypeCrudService contractType_service = Factories.service
        .forContractTypeCrudService();

    @Override
    public void execute() throws BusinessException {
        String name = Console.readString("Contract type name");
        double compensationDays = Console.readDouble("Compensation days");
        ContractTypeDto c = new ContractTypeDto();
        c.name = name;
        c.compensationDays = compensationDays;
        contractType_service.create(c);
        Console.println("Contract type registered");
    }

}