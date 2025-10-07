package uo.ri.cws.application.ui.manager.mechanic.action;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class ListMechanicAction implements Action {

    @Override
    public void execute() throws BusinessException {

        String nif = Console.readString("nif"); 
        MechanicCrudService service = Factories.service.forMechanicCrudService();
        Optional<MechanicDto> result = service.findByNif(nif);
        if(result.isEmpty()) {
        	Console.println("There is no mechanic with that nif");
        }else {
        	MechanicDto m = result.get();
        	Console.println("\nMechanic information \n");
            Console.printf("\t%s %s %s %s %d\n",
            		m.id,
            		m.name,
            		m.surname,
            		m.nif,
            		m.version);
        }
    }
}