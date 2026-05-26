package uo.ri.cws.application.persistence.professionalgroup.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupRecord;

public class ProfessionalGroupAssembler {

	public static Optional<ProfessionalGroupRecord> toRecord(ResultSet rs)
			throws SQLException {
		Optional<ProfessionalGroupRecord> result = Optional.empty();
		if (rs.next()) {
			ProfessionalGroupRecord m = new ProfessionalGroupRecord();
			m.id = rs.getString("id");
			m.createAt = rs.getTimestamp("createdAt").toLocalDateTime();
			m.updateAt = rs.getTimestamp("updatedAt").toLocalDateTime();
			m.entityState = rs.getString("entityState");
			m.name = rs.getString("name");
			m.productivityRate = rs.getDouble("productivityRate");
			m.trienniumPayment = rs.getDouble("trienniumPayment");
			m.version = rs.getLong("version");
			result = Optional.of(m);
		}

		return result;
	}

	public static List<ProfessionalGroupRecord> toRecordList(ResultSet rs)
			throws SQLException {
		List<ProfessionalGroupRecord> result = new ArrayList<ProfessionalGroupRecord>();
		while (rs.next()) {
			ProfessionalGroupRecord m = new ProfessionalGroupRecord();
			m.id = rs.getString("id");
			m.createAt = rs.getTimestamp("createdAt").toLocalDateTime();
			m.updateAt = rs.getTimestamp("updatedAt").toLocalDateTime();
			m.entityState = rs.getString("entityState");
			m.name = rs.getString("name");
			m.productivityRate = rs.getDouble("productivityRate");
			m.trienniumPayment = rs.getDouble("trienniumPayment");
			m.version = rs.getLong("version");
			result.add(m);
		}

		return result;
	}

}
