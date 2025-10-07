package uo.ri.cws.application.ui.manager.mechanic.action;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class ListAllMechanicsAction implements Action {

   
    @Override
    public void execute() throws BusinessException {

    	MechanicCrudService service = Factories.service.forMechanicCrudService();
    	List<MechanicDto> listWithAllTheMechanics = service.findAll();
        Console.println("\nList of mechanics \n");
        for(MechanicDto m : listWithAllTheMechanics) {
        	 Console.printf("\t%s %s %s %s %d\n",
                     m.id,
                     m.nif,
                     m.name,
                     m.surname,
                     m.version);
        }
    }
}

