package uo.ri.cws.application.persistence.workorder.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.util.executor.Jdbc;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.util.jdbc.Queries;

public class WorkOrderGatewayImpl implements WorkOrderGateway {

    @Override
    public void add(WorkOrderRecord t) throws PersistenceException {
        // TODO Auto-generated method stub

    }

    @Override
    public void remove(String id) throws PersistenceException {
        // TODO Auto-generated method stub

    }

    @Override
    public void update(WorkOrderRecord t) throws PersistenceException {
        // TODO Auto-generated method stub

    }

    @Override
    public Optional<WorkOrderRecord> findById(String id) {
        Connection c = Jdbc.getCurrentConnection();
        Optional<WorkOrderRecord> optional = Optional.empty();
        WorkOrderRecord record = new WorkOrderRecord();
        try (PreparedStatement pst = c.prepareStatement(
                Queries.getSQLSentence("TWORKORDERS_FIND_BY_ID"))) {
            pst.setString(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    record.id = rs.getString(1);
                    optional = Optional.of(record);
                    return optional;
                } else {
                    return optional;
                }

            }
        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }

    @Override
    public List<WorkOrderRecord> findAll() throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String findStatusById(String id) throws PersistenceException {

        Connection connection = Jdbc.getCurrentConnection();
        String state = "";
        try (PreparedStatement pst = connection.prepareStatement(
                Queries.getSQLSentence("TWORKORDERS_FIND_STATUS_BY_ID"))) {
            pst.setString(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    state = rs.getString(1);
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException();
        }

        return state;
    }

    @Override
    public double findAmountById(String id) throws PersistenceException {
        Connection connection = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = connection.prepareStatement(
                Queries.getSQLSentence("TWORKORDERS_FIND_AMOUNT_BY_ID"))) {
            pst.setString(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("amount");
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException();
        }
        return 0.0;
    }

    @Override
    public void updateState(String id) throws PersistenceException {

        Connection connection = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = connection.prepareStatement(
                Queries.getSQLSentence("TWORKORDERS_UPDATE_STATE"))) {
            pst.setString(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }

    @Override
    public void updateInvoiceId(String id, String invoiceId)
            throws PersistenceException {

        Connection connection = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = connection.prepareStatement(
                Queries.getSQLSentence("TWORKORDERS_UPDATE_INVOICEID"))) {
            pst.setString(1, invoiceId);
            pst.setString(2, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenceException();
        }

    }

    @Override
    public void updateVersion(String id) throws PersistenceException {
        Connection c = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = c.prepareStatement(
                Queries.getSQLSentence("TWORKORDERS_UPDATE_VERSION"))) {
            pst.setString(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenceException();
        }

    }

    @Override
    public void updateTimeStamp(String id) throws PersistenceException {

        Connection c = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = c.prepareStatement(
                Queries.getSQLSentence("TWORKORDERS_UPDATE_TIMESTAMP"))) {
            pst.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            pst.setString(2, id);
            pst.executeUpdate();

        } catch (SQLException e) {
            throw new PersistenceException();
        }

    }

    @Override
    public Optional<MechanicRecord> findWorkordersByMechanicId(
            String mechanic_id) throws PersistenceException {

        Optional<MechanicRecord> mechanic = Optional.empty();
        Connection connection = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = connection.prepareStatement(
                Queries.getSQLSentence("TWORKORDERS_FIND_ID_BY_MECHANIC_ID"))) {
            pst.setString(1, mechanic_id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    mechanic = Optional.of(new MechanicRecord());
                    mechanic.get().id = rs.getString(1);
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException();
        }

        return mechanic;
    }

    @Override
    public List<WorkOrderRecord> findNotInvoicedWorkOrdersByClientNif(
            String client_nif) throws PersistenceException {
        List<WorkOrderRecord> invoicingWOs = new ArrayList<WorkOrderRecord>();

        Connection c = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = c.prepareStatement(
                Queries.getSQLSentence("TWORKORDERS_FIND_NOT_INVOICED"))) {
            pst.setString(1, client_nif);
            try (ResultSet rs = pst.executeQuery();) {
                while (rs.next()) {
                    WorkOrderRecord w = new WorkOrderRecord();
                    w.id = rs.getString(1);
                    w.description = rs.getString(2);
                    w.date = rs.getTimestamp(3).toLocalDateTime().toLocalDate();
                    w.state = rs.getString(4);
                    w.amount = rs.getDouble(5);
                    invoicingWOs.add(w);
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException();
        }
        return invoicingWOs;
    }

}
