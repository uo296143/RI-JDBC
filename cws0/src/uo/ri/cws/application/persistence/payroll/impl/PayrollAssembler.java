package uo.ri.cws.application.persistence.payroll.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollRecord;

public class PayrollAssembler {

	public static Optional<PayrollRecord> toRecord(ResultSet rs)
			throws SQLException {
		Optional<PayrollRecord> result = Optional.empty();
		if (rs.next()) {
			PayrollRecord m = new PayrollRecord();
			m.id = rs.getString("id");
			m.createAt = rs.getTimestamp("createdAt").toLocalDateTime();
			m.updateAt = rs.getTimestamp("updatedAt").toLocalDateTime();
			m.entityState = rs.getString("entityState");
			m.baseSalary = rs.getDouble("baseSalary");
			m.version = rs.getLong("version");
			m.date = rs.getDate("date").toLocalDate();
			m.extraSalary = rs.getDouble("extraSalary");
			m.nicDeduction = rs.getDouble("nicDeduction");
			m.productivityEarning = rs.getDouble("productivityEarning");
			m.taxDeduction = rs.getDouble("taxDeduction");
			m.contractId = rs.getString("contract_id");
			m.trienniumEarning = rs.getDouble("trienniumEarning");
			result = Optional.of(m);
		}

		return result;
	}

	public static List<PayrollRecord> toRecordList(ResultSet rs)
			throws SQLException {
		List<PayrollRecord> result = new ArrayList<PayrollRecord>();
		while (rs.next()) {
			PayrollRecord m = new PayrollRecord();
			m.id = rs.getString("id");
			m.createAt = rs.getTimestamp("createdAt").toLocalDateTime();
			m.updateAt = rs.getTimestamp("updatedAt").toLocalDateTime();
			m.entityState = rs.getString("entityState");
			m.baseSalary = rs.getDouble("baseSalary");
			m.version = rs.getLong("version");
			m.date = rs.getDate("date").toLocalDate();
			m.extraSalary = rs.getDouble("extraSalary");
			m.nicDeduction = rs.getDouble("nicDeduction");
			m.productivityEarning = rs.getDouble("productivityEarning");
			m.taxDeduction = rs.getDouble("taxDeduction");
			m.contractId = rs.getString("contract_id");
			m.trienniumEarning = rs.getDouble("trienniumEarning");
			result.add(m);
		}

		return result;
	}

}
