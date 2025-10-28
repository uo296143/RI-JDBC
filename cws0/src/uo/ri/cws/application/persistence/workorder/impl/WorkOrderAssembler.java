package uo.ri.cws.application.persistence.workorder.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderRecord;

public class WorkOrderAssembler {
	
	public static Optional<WorkOrderRecord> toRecord(ResultSet rs) throws SQLException {
		 Optional<WorkOrderRecord> result = Optional.empty();
		 if(rs.next()) {
			 WorkOrderRecord m = new WorkOrderRecord();
			 m.id = rs.getString("id");
	         m.createAt = rs.getTimestamp("createdAt").toLocalDateTime();
	         m.updateAt = rs.getTimestamp("updatedAt").toLocalDateTime();
	         m.entityState = rs.getString("entityState");
	         m.date = rs.getDate("date").toLocalDate();
	         m.number = rs.getLong("number");
	         m.amount = rs.getDouble("amount");
	         m.version = rs.getLong("version");
	         m.invoice_id = rs.getString("invoice_id");
	         m.mechanic_id = rs.getString("mechanic_id");
	         m.vehicle_id = rs.getString("vehicle_id");
	         m.description = rs.getString("description");
	         m.state = rs.getString("state");
	         result = Optional.of(m);
		 }
       
       return result;
	}

}
