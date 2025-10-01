package uo.ri.cws.application.service.mechanic.crud.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.util.jdbc.Jdbc;

public class ListMechanicByNif {

	 private static final String TMECHANICS_FINDBYNIF = 
	            "SELECT ID, NAME, SURNAME, nif, VERSION FROM TMECHANICS "
	                    + "WHERE NIF = ?";
	 
	 private String nif;
	 
	 public ListMechanicByNif(String nif) {
		 this.nif = nif;
	 }
	 
	 public Optional<MechanicDto> execute() {
		 Optional<MechanicDto> result = Optional.empty();
		 try (Connection c = Jdbc.createThreadConnection()) {
	            try (PreparedStatement pst = c
	                    .prepareStatement(TMECHANICS_FINDBYNIF)) {
	                pst.setString(1, nif);
	                try (ResultSet rs = pst.executeQuery()) {
	                    if (rs.next()) {
	                        MechanicDto m = new MechanicDto();
	                        m.id = rs.getString(1);
	                        m.nif = rs.getString(2);
	                        m.name = rs.getString(3);
	                        m.surname = rs.getString(4);
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
