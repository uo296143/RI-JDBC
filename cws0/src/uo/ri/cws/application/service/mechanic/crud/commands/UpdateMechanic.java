package uo.ri.cws.application.service.mechanic.crud.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.persistence.util.executor.Jdbc;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.MechanicAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class UpdateMechanic implements Command<Void>{

	private MechanicGateway m = Factories.persistence.forMechanic();
	
	private static final String TMECHANICS_UPDATE = "update TMechanics set name = ?, surname = ?, "
			+ "version = version + 1, updatedat = ?" + "where id = ?";

	
	/**
	 * Name and surname cannot be null nor blank
	 * @param arg
	 */
	public UpdateMechanic(MechanicDto arg) {
		ArgumentChecks.isNotNull(arg);
		ArgumentChecks.isNotBlank(arg.id);
		ArgumentChecks.isNotNull(arg.id);
		ArgumentChecks.isNotBlank(arg.name);
		ArgumentChecks.isNotNull(arg.name);
		ArgumentChecks.isNotBlank(arg.surname);
		ArgumentChecks.isNotNull(arg.surname);
		m = arg;
	}

	public Void execute() throws BusinessException {
		MechanicRecord readFromDatabase = checkMechanicExists(m.id); // Se hace la comprobacion en 2 sitios.
		BusinessChecks.hasVersion(m.version, readFromDatabase.version, "Staled data");
		Factories.persistence.forMechanic().update(readFromDatabase); // REfacyoica atirui
	}
	
	private MechanicRecord checkMechanicExists(String id) throws BusinessException{
		Optional<MechanicRecord> optional = Factories.persistence.forMechanic().findById(id);
		BusinessChecks.isTrue(optional.isPresent(), "Mechanic does not exist");
		return optional.get();
	}

}
