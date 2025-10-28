package uo.ri.cws.application.persistence.contract.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;

public class ContractAssembler {

    public static Optional<ContractRecord> toRecord(ResultSet rs)
            throws SQLException {
        Optional<ContractRecord> result = Optional.empty();
        if (rs.next()) {
            ContractRecord m = new ContractRecord();
            m.id = rs.getString("id");
            m.version = rs.getLong("version");
            m.createAt = rs.getTimestamp("createdAt").toLocalDateTime();
            m.updateAt = rs.getTimestamp("updatedAt").toLocalDateTime();
            m.entityState = rs.getString("entityState");

            m.annualBaseSalary = rs.getDouble("annualBaseSalary");
            m.contractTypeId = rs.getString("contractType_Id");
            m.mechanicId = rs.getString("mechanic_Id");
            m.professionalGroupId = rs.getString("professionalGroup_Id");
            m.settlement = rs.getDouble("settlement");
            if (m.endDate != null)
                m.endDate = rs.getDate("endDate").toLocalDate();
            m.startDate = rs.getDate("startDate").toLocalDate();
            m.state = rs.getString("state");
            m.taxRate = rs.getDouble("taxRate");

            result = Optional.of(m);
        }

        return result;
    }
}
