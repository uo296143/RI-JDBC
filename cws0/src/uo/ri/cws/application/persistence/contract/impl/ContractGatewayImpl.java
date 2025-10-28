package uo.ri.cws.application.persistence.contract.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.util.executor.Jdbc;
import uo.ri.util.jdbc.Queries;

public class ContractGatewayImpl implements ContractGateway {

    @Override
    public void add(ContractRecord t) throws PersistenceException {

        Connection c = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = c
            .prepareStatement(Queries.getSQLSentence("TCONTRACTS_ADD"))) {
            pst.setString(1, t.mechanicId);
            pst.setString(2, t.contractTypeId);
            pst.setString(3, t.professionalGroupId);
            pst.setDouble(4, t.annualBaseSalary);
            pst.setString(5, t.id);
            pst.setLong(6, t.version);
            pst.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
            pst.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
            pst.setString(9, "ENABLED");
            pst.setDouble(10, t.settlement);
            pst.setDate(11, Date.valueOf(t.startDate));
            pst.setString(12, t.state);
            pst.setDouble(13, t.taxRate);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }

    @Override
    public void remove(String id) throws PersistenceException {

        Connection c = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = c
            .prepareStatement(Queries.getSQLSentence("TCONTRACTS_DELETE"))) {
            pst.setString(1, id);
            pst.executeUpdate();
        }

        catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void update(ContractRecord t) throws PersistenceException {

        Connection c = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = c
            .prepareStatement(Queries.getSQLSentence("TCONTRACTS_UPDATE"))) {
            pst.setDate(1, Date.valueOf(t.endDate));
            pst.setDouble(2, t.annualBaseSalary);
            pst.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            pst.setString(4, t.id);
            pst.executeUpdate();

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }

    }

    @Override
    public Optional<ContractRecord> findById(String id)
            throws PersistenceException {

        Connection c = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = c
            .prepareStatement(Queries.getSQLSentence("TCONTRACTS_FINDBYID"))) {
            pst.setString(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                return ContractAssembler.toRecord(rs);
            }

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<ContractRecord> findAll() throws PersistenceException {

        List<ContractRecord> listOfAllContracts = new ArrayList<ContractRecord>();
        Connection c = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = c
            .prepareStatement(Queries.getSQLSentence("TCONTRACTS_FINDALL"))) {
            try (ResultSet rs = pst.executeQuery();) {
                while (rs.next()) {
                    ContractRecord m = new ContractRecord();
                    m.id = rs.getString("id");
                    m.version = rs.getLong("version");
                    m.createAt = rs.getTimestamp("createdAt").toLocalDateTime();
                    m.updateAt = rs.getTimestamp("updatedAt").toLocalDateTime();
                    m.entityState = rs.getString("entityState");

                    m.annualBaseSalary = rs.getDouble("annualBaseSalary");
                    m.contractTypeId = rs.getString("contractType_Id");
                    m.mechanicId = rs.getString("mechanic_Id");
                    m.professionalGroupId = rs
                        .getString("professionalGroup_Id");
                    m.settlement = rs.getDouble("settlement");
                    if (m.endDate != null)
                        m.endDate = rs.getDate("endDate").toLocalDate();
                    m.startDate = rs.getDate("startDate").toLocalDate();
                    m.state = rs.getString("state");
                    m.taxRate = rs.getDouble("taxRate");
                    listOfAllContracts.add(m);
                }
            }

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }

        return listOfAllContracts;
    }

    @Override
    public List<ContractRecord> findContractsByMechanicId(String mechanic_id)
            throws PersistenceException {

        List<ContractRecord> list = new ArrayList<ContractRecord>();
        Connection c = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = c.prepareStatement(
                Queries.getSQLSentence("TCONTRACTS_FIND_BY_MECHANIC_ID"))) {
            pst.setString(1, mechanic_id);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    ContractRecord m = new ContractRecord();
                    m.id = rs.getString("id");
                    m.version = rs.getLong("version");
                    m.createAt = rs.getTimestamp("createdAt").toLocalDateTime();
                    m.updateAt = rs.getTimestamp("updatedAt").toLocalDateTime();
                    m.entityState = rs.getString("entityState");

                    m.annualBaseSalary = rs.getDouble("annualBaseSalary");
                    m.contractTypeId = rs.getString("contractType_Id");
                    m.mechanicId = rs.getString("mechanic_Id");
                    m.professionalGroupId = rs
                        .getString("professionalGroup_Id");
                    m.settlement = rs.getDouble("settlement");
                    if (m.endDate != null)
                        m.endDate = rs.getDate("endDate").toLocalDate();
                    m.startDate = rs.getDate("startDate").toLocalDate();
                    m.state = rs.getString("state");
                    m.taxRate = rs.getDouble("taxRate");
                    list.add(m);
                }
            }

            return list;

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Optional<ContractRecord> findByMechanicId(String mechanic_id)
            throws PersistenceException {

        Connection c = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = c.prepareStatement(
                Queries.getSQLSentence("TCONTRACTS_FIND_BY_MECHANIC_ID"))) {
            pst.setString(1, mechanic_id);
            try (ResultSet rs = pst.executeQuery()) {
                return ContractAssembler.toRecord(rs);
            }

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void finishContract(String id, LocalDate endDate)
            throws PersistenceException {

        Connection c = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = c.prepareStatement(
                Queries.getSQLSentence("TCONTRACTS_FINISH_CONTRACT"))) {
            pst.setDate(1, Date.valueOf(endDate));
            pst.setString(2, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }

    }

    @Override
    public void insertSettlement(double settlement, String id)
            throws PersistenceException {

        Connection c = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = c.prepareStatement(
                Queries.getSQLSentence("TCONTRACTS_INSERT_SETTLEMENT"))) {
            pst.setDouble(1, settlement);
            pst.setString(2, id);
            pst.executeUpdate();

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }

    }

    @Override
    public Optional<String> findMechanicIdByContractId(String contract_id)
            throws PersistenceException {

        Connection c = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = c.prepareStatement(Queries
            .getSQLSentence("TCONTRACTS_FIND_MECHANIC_ID_BY_CONTRACT_ID"))) {
            pst.setString(1, contract_id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    String mechanic_id = rs.getString(1);
                    return Optional.of(mechanic_id);
                } else {
                    return Optional.empty();
                }

            }

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

}
