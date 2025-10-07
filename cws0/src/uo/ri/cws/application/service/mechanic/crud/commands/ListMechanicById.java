package uo.ri.cws.application.service.mechanic.crud.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.jdbc.Jdbc;

public class ListMechanicById {

	 private static final String TMECHANICS_FINDBYID = 
	            "SELECT ID, NAME, SURNAME, nif, VERSION FROM TMECHANICS "
	                    + "WHERE ID = ?";
	 
	 private String id;
	 
	 public ListMechanicById(String id) {
		 ArgumentChecks.isNotNull(id);
		 this.id = id;
	 }
	 
	 public Optional<MechanicDto> execute() {
		 Optional<MechanicDto> result = Optional.empty();
		 try (Connection c = Jdbc.createThreadConnection()) {
	            try (PreparedStatement pst = c
	                    .prepareStatement(TMECHANICS_FINDBYID)) {
	                pst.setString(1, id);
	                try (ResultSet rs = pst.executeQuery()) {
	                    if (rs.next()) {
	                        MechanicDto m = new MechanicDto();
	                        m.id = rs.getString(1);
	                        m.name = rs.getString(2);
	                        m.surname = rs.getString(3);
	                        m.nif = rs.getString(4);
	                        m.version = rs.getLong(5);
	                        result = Optional.of(m);
	                    }
	                }
	            }
	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        }
		 return result;
	 }
}
