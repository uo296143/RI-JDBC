package uo.ri.cws.application.ui.manager.mechanic.action;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class UpdateMechanicAction implements Action {

	private MechanicCrudService service = Factories.service.forMechanicCrudService();

	@Override
	public void execute() throws BusinessException {

		// Get info
		String id = Console.readString("Type mechahic id to update");

		// check mechanic exists
		Optional<MechanicDto> optional = service.findById(id);
		if(optional.isEmpty()) {
			Console.print("There is no mechanic with that id");
			return;
		}
		
		MechanicDto m = optional.get();
		m.id = id;
		// Ask for new data
		// nif is the identity, cannot be changed
		m.name = Console.readString("Name");
		m.surname = Console.readString("Surname");

		// update
		service.update(m);
		// Print result
		Console.println("Mechanic updated");

	}

}