package uo.ri.cws.application.persistence.invoice.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway;
import uo.ri.cws.application.persistence.util.executor.Jdbc;
import uo.ri.util.jdbc.Queries;

public class InvoiceGatewayImpl implements InvoiceGateway {

    @Override
    public void add(InvoiceRecord t) throws PersistenceException {

        Connection connection = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = connection
            .prepareStatement(Queries.getSQLSentence("TINVOICES_ADD"))) {
            pst.setString(1, t.id);
            pst.setLong(2, t.number);
            pst.setDate(3, java.sql.Date.valueOf(t.date));
            pst.setDouble(4, t.vat);
            pst.setDouble(5, t.amount);
            pst.setString(6, t.state);
            pst.setLong(7, t.version);
            pst.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
            pst.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
            pst.setString(10, "ENABLED");
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenceException();
        }

    }

    @Override
    public void remove(String id) throws PersistenceException {
        // TODO Auto-generated method stub

    }

    @Override
    public void update(InvoiceRecord t) throws PersistenceException {
        // TODO Auto-generated method stub

    }

    @Override
    public Optional<InvoiceRecord> findById(String id)
            throws PersistenceException {

        Connection c = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = c
            .prepareStatement(Queries.getSQLSentence("TINVOICES_FINDBYID"))) {
            pst.setString(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                return InvoiceAssembler.toRecord(rs);
            }
        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }

    @Override
    public List<InvoiceRecord> findAll() throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long findNextNumber() throws PersistenceException {
        Connection connection = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = connection.prepareStatement(
                Queries.getSQLSentence("TINVOICES_FINDNEXTNUMBER"))) {
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1) + 1;
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException();
        }
        return 1L; // Si no hay facturas previas, empezamos desde 1
    }

}
