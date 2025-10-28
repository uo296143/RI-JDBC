package uo.ri.cws.application.persistence.contracttype.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway;
import uo.ri.cws.application.persistence.util.executor.Jdbc;
import uo.ri.util.jdbc.Queries;

public class ContractTypeGatewayImpl implements ContractTypeGateway {

    @Override
    public void add(ContractTypeRecord t) throws PersistenceException {

        Connection c = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = c
            .prepareStatement(Queries.getSQLSentence("TCONTRACTTYPES_ADD"))) {
            pst.setString(1, t.id);
            pst.setDouble(2, t.compensationDaysPerYear);
            pst.setLong(3, t.version);
            pst.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            pst.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            pst.setString(6, "ENABLED");
            pst.setString(7, t.name);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }

    }

    /**
     * @param Id refers to the name of the contract type.
     */
    @Override
    public void remove(String id) throws PersistenceException {

        Connection c = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = c.prepareStatement(
                Queries.getSQLSentence("TCONTRACTTYPES_DELETE"))) {
            pst.setString(1, id);
            pst.executeUpdate();
        }

        catch (SQLException e) {
            throw new PersistenceException(e);
        }

    }

    @Override
    public void update(ContractTypeRecord t) throws PersistenceException {

        Connection c = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = c.prepareStatement(
                Queries.getSQLSentence("TCONTRACTTYPES_UPDATE"))) {
            pst.setDouble(1, t.compensationDaysPerYear);
            pst.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            pst.setString(3, t.id);
            pst.executeUpdate();

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Optional<ContractTypeRecord> findById(String id)
            throws PersistenceException {

        Connection c = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = c.prepareStatement(
                Queries.getSQLSentence("TCONTRACTTYPES_FINDBYID"))) {
            pst.setString(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                return ContractTypeAssembler.toRecord(rs);
            }

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<ContractTypeRecord> findAll() throws PersistenceException {

        List<ContractTypeRecord> listOfAllContracts = new ArrayList<ContractTypeRecord>();
        Connection c = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = c.prepareStatement(
                Queries.getSQLSentence("TCONTRACTTYPES_FIND_ALL"))) {
            try (ResultSet rs = pst.executeQuery();) {
                while (rs.next()) {
                    ContractTypeRecord m = new ContractTypeRecord();
                    m.id = rs.getString("id");
                    m.version = rs.getLong("version");
                    m.createAt = rs.getTimestamp("createdAt").toLocalDateTime();
                    m.updateAt = rs.getTimestamp("updatedAt").toLocalDateTime();
                    m.entityState = rs.getString("entityState");

                    m.name = rs.getString("name");
                    m.compensationDaysPerYear = rs
                        .getDouble("compensationDaysPerYear");
                    listOfAllContracts.add(m);
                }
            }

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }

        return listOfAllContracts;
    }

    @Override
    public Optional<ContractTypeRecord> findByName(String name)
            throws PersistenceException {

        Connection c = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = c.prepareStatement(
                Queries.getSQLSentence("TCONTRACTTYPES_FINDBYNAME"))) {
            pst.setString(1, name);
            try (ResultSet rs = pst.executeQuery()) {
                return ContractTypeAssembler.toRecord(rs);
            }

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

}
