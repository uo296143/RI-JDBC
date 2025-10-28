package uo.ri.cws.application.ui.manager.mechanic.action;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

/**
 * Action that deletes a mechanic by id.
 */
public class DeleteMechanicAction implements Action {

    /* Service used to perform mechanic CRUD operations. */
    private final MechanicCrudService service = Factories.service
        .forMechanicCrudService();

    /**
     * Reads a mechanic id from the console and deletes it if present.
     *
     * @throws BusinessException if the service layer reports a business error
     */
    @Override
    public void execute() throws BusinessException {
        String idMechanic = Console.readString("Type mechanic id ");
        Optional<MechanicDto> mechanic = service.findById(idMechanic);
        if (mechanic.isEmpty()) {
            Console.println(
                    "The mechanic couldn´t be deleted because he doesn´t exist");
        } else {
            service.delete(idMechanic);
            Console.println("Mechanic deleted");
        }
    }

}
