package uo.ri.cws.application.ui.manager.mechanic.action;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

/**
 * Action that updates a mechanic
 */
public class UpdateMechanicAction implements Action {

    /* Service used to perform mechanic CRUD operations. */
    private final MechanicCrudService service = Factories.service
        .forMechanicCrudService();

    /**
     * Update a mechanic if exists, you can only change name and surname
     *
     * @throws BusinessException if the service layer reports a business error
     */
    @Override
    public void execute() throws BusinessException {
        String id = Console.readString("Type mechahic id to update");
        Optional<MechanicDto> optional = service.findById(id);
        if (optional.isEmpty()) {
            Console
                .println("There isn´t a mechanic in the system with that id");
        } else {
            MechanicDto m = optional.get();
            m.id = id;
            m.name = Console.readString("Name");
            m.surname = Console.readString("Surname");
            service.update(m);
            Console.println("Mechanic updated");
        }
    }

}