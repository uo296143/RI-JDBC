package uo.ri.cws.application.ui.manager.contracts.contract.action;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.contract.ContractCrudService;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractDto;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class UpdateContractAction implements Action {

    private final ContractCrudService contract_service = Factories.service
        .forContractCrudService();

    @Override
    public void execute() throws BusinessException {

        String id = Console.readString("Contract id");
        Optional<ContractDto> c = contract_service.findById(id);
        if (c.isEmpty()) {
            Console.println("Contract is not on the system");
        } else {
            contract_service.update(c.get());
            Console.println("Contract updated");
        }

    }

//    private LocalDate askOptionalForDate(String msg) {
//        while (true) {
//            try {
//                Console.print(msg + " [optional]: ");
//                String asString = Console.readString();
//                return ("".equals(asString)) ? null : LocalDate.parse(asString);
//            } catch (Exception e) {
//                Console.println("--> Invalid date");
//            }
//        }
//    }
}