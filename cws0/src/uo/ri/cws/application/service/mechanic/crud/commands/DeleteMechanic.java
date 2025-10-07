package uo.ri.cws.application.service.mechanic.crud.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.jdbc.Jdbc;

public class DeleteMechanic {

	private String mechanicId;
	private static final String TMECHANICS_DELETE = "DELETE FROM TMECHANICS "
	            + "WHERE ID = ?";
	
	public DeleteMechanic(String mechanicId) {
		ArgumentChecks.isNotNull(mechanicId);
		this.mechanicId = mechanicId;
	}
	
	public void execute() {
		// Process
        try (Connection c = Jdbc.createThreadConnection();) {
            try (PreparedStatement pst = c
                    .prepareStatement(TMECHANICS_DELETE)) {
                pst.setString(1, mechanicId);
                pst.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}

}
