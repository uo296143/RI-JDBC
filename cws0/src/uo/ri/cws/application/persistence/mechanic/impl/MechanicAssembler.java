package uo.ri.cws.application.persistence.mechanic.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;

public class MechanicAssembler {
	
	public static Optional<MechanicRecord> toRecord(ResultSet rs) throws SQLException {
		 Optional<MechanicRecord> result = Optional.empty();
		 if(rs.next()) {
			 MechanicRecord m = new MechanicRecord();
			 m.id = rs.getString("id");
	         m.createAt = rs.getTimestamp("createAt").toLocalDateTime();
	         m.updateAt = rs.getTimestamp("updateAt").toLocalDateTime();
	         m.entityState = rs.getString("entityState");
	         m.name = rs.getString("name");
	         m.surname = rs.getString("surname");
	         m.nif = rs.getString("nif");
	         m.version = rs.getLong("version");
	         result = Optional.of(m);
		 }
         
         return result;
	}

}
