package uo.ri.cws.application.persistence.invoice.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import uo.ri.cws.application.persistence.invoice.InvoiceGateway.InvoiceRecord;

public class InvoiceAssembler {

    public static Optional<InvoiceRecord> toRecord(ResultSet rs)
            throws SQLException {
        Optional<InvoiceRecord> result = Optional.empty();
        if (rs.next()) {
            InvoiceRecord m = new InvoiceRecord();
            m.id = rs.getString("id");
            m.createAt = rs.getTimestamp("createdAt").toLocalDateTime();
            m.updateAt = rs.getTimestamp("updatedAt").toLocalDateTime();
            m.entityState = rs.getString("entityState");
            m.date = rs.getDate("date").toLocalDate();
            m.number = rs.getLong("number");
            m.vat = rs.getDouble("vat");
            m.state = rs.getString("state");
            m.version = rs.getLong("version");
            result = Optional.of(m);
        }

        return result;
    }
}
