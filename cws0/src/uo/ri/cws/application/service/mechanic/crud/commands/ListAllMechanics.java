package uo.ri.cws.application.service.mechanic.crud.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import uo.ri.cws.application.persistence.util.executor.Jdbc;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;

public class ListAllMechanics {
	
	private static final String TMECHANICS_FINDALL = "SELECT ID, NAME, "
	            + "SURNAME, NIF, VERSION FROM TMECHANICS";


	public List<MechanicDto> execute() {
		List<MechanicDto> listOfAllMechanics = new ArrayList<MechanicDto>();
        try (Connection c = Jdbc.createThreadConnection()) {
            try (PreparedStatement pst = c
                    .prepareStatement(TMECHANICS_FINDALL)) {
                try (ResultSet rs = pst.executeQuery();) {
                    while (rs.next()) {
                    	MechanicDto m = new MechanicDto();
                    	m.id = rs.getString(1);
                        m.name = rs.getString(2);
                        m.surname = rs.getString(3);
                        m.nif = rs.getString(4);
                        m.version = rs.getLong(5);
                    	listOfAllMechanics.add(m);    
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        
		return listOfAllMechanics;
	}  

}
