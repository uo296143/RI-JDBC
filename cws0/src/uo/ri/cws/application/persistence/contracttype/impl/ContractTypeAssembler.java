package uo.ri.cws.application.persistence.contracttype.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway.ContractTypeRecord;

public class ContractTypeAssembler {

    public static Optional<ContractTypeRecord> toRecord(ResultSet rs)
            throws SQLException {
        Optional<ContractTypeRecord> result = Optional.empty();
        if (rs.next()) {
            ContractTypeRecord m = new ContractTypeRecord();
            m.id = rs.getString("id");
            m.createAt = rs.getTimestamp("createdAt").toLocalDateTime();
            m.updateAt = rs.getTimestamp("updatedAt").toLocalDateTime();
            m.entityState = rs.getString("entityState");
            m.name = rs.getString("name");
            m.version = rs.getLong("version");
            m.compensationDaysPerYear = rs.getDouble("compensationDaysPerYear");
            result = Optional.of(m);
        }

        return result;
    }

}
