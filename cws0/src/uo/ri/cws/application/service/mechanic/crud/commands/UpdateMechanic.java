package uo.ri.cws.application.service.mechanic.crud.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.jdbc.Jdbc;

public class UpdateMechanic {

	private static final String TMECHANICS_UPDATE = "update TMechanics set name = ?, surname = ?, "
			+ "version = version + 1, updatedat = ?" + "where id = ?";

	private MechanicDto m;
	
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

	public Void execute() {
		// Process
		try (Connection c = Jdbc.createThreadConnection()) {
			try (PreparedStatement pst = c.prepareStatement(TMECHANICS_UPDATE)) {
				pst.setString(1, m.name);
				pst.setString(2, m.surname);
				pst.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
				pst.setString(4, m.id);

				pst.executeUpdate();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return null;
	}

}
