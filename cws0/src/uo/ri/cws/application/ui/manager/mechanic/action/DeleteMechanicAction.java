package uo.ri.cws.application.ui.manager.mechanic.action;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class DeleteMechanicAction implements Action {
	
    MechanicCrudService service = Factories.service.forMechanicCrudService();

	/**
	 * Se comprueba que no ocurra lo siguiente : 
	 * - the mechanic does not exist
	 * - the mechanic has workorders assigned
	 * - the mechanic has interventions done
	 * - the mechanic has contracts
	 */
    @Override
    public void execute() throws BusinessException {
        String idMechanic = Console.readString("Type mechanic id ");
    	BusinessChecks.exists(service.findById(idMechanic), "The mechanic couldn´t be deleted because he doesn´t exist.");
        service.delete(idMechanic);
        Console.println("Mechanic deleted");
    }

}




