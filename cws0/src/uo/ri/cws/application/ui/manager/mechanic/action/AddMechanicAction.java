package uo.ri.cws.application.ui.manager.mechanic.action;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

/**
 * Action that add a mechanic
 */
public class AddMechanicAction implements Action {

    /* Service used to perform mechanic CRUD operations. */
    private final MechanicCrudService service = Factories.service
        .forMechanicCrudService();

    /**
     * Reads nif, name and surname to create a new mechanic.
     *
     * @throws BusinessException if the service layer reports a business error
     */
    @Override
    public void execute() throws BusinessException {
        MechanicDto m = new MechanicDto();
        m.nif = Console.readString("nif");
        m.name = Console.readString("Name");
        m.surname = Console.readString("Surname");
        service.create(m);
        Console.println("Mechanic added");
    }

}
