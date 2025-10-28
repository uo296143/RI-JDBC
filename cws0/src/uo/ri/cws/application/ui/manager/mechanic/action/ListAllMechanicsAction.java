package uo.ri.cws.application.ui.manager.mechanic.action;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

/**
 * Action that list all the mechanics on the system
 */
public class ListAllMechanicsAction implements Action {

    /* Service used to perform mechanic CRUD operations. */
    private final MechanicCrudService service = Factories.service
        .forMechanicCrudService();

    /**
     * List all the mechanics of the system
     *
     * @throws BusinessException if the service layer reports a business error
     */
    @Override
    public void execute() throws BusinessException {

        List<MechanicDto> listWithAllTheMechanics = service.findAll();
        Console.println("\nList of mechanics \n");
        for (MechanicDto m : listWithAllTheMechanics) {
            Console.printf("\t%s %s %s %s %d\n", m.id, m.nif, m.name, m.surname,
                    m.version);
        }
    }
}
