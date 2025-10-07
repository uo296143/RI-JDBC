package uo.ri.cws.application.ui.manager.mechanic.action;


import uo.ri.conf.Factories;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class AddMechanicAction implements Action {
    
    MechanicCrudService service = Factories.service.forMechanicCrudService();

    @Override
    public void execute() throws BusinessException {
    	
    	MechanicDto m = new MechanicDto();
        m.nif = Console.readString("nif");
        m.name = Console.readString("Name");
        m.surname = Console.readString("Surname");
        
		BusinessChecks.doesNotExist(service.findByNif(m.nif));
		
        service.create(m);
        
        // Print result
        Console.println("Mechanic added");
    }

}
   