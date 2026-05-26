package uo.ri.cws.application.persistence.professionalgroup.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway;
import uo.ri.cws.application.persistence.util.executor.Jdbc;
import uo.ri.util.jdbc.Queries;

public class ProfessionalGroupGatewayImpl implements ProfessionalGroupGateway {

    @Override
    public void add(ProfessionalGroupRecord t) throws PersistenceException {
    	Connection c = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = c.prepareStatement(
                Queries.getSQLSentence("TPROFESSIONALGROUPS_ADD"))) {            
            pst.setString(1, t.id);
            pst.setLong(2, t.version);
            pst.setTimestamp(3, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
            pst.setTimestamp(4, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now())); 
            pst.setString(5, "ACTIVE");
            pst.setString(6, t.name);
            pst.setDouble(7, t.productivityRate);
            pst.setDouble(8, t.trienniumPayment);
            pst.executeUpdate(); 
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void remove(String id) throws PersistenceException {
    	Connection c = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = c.prepareStatement(
                Queries.getSQLSentence("TPROFESSIONALGROUPS_REMOVE"))) {
            pst.setString(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void update(ProfessionalGroupRecord t) throws PersistenceException {
    	Connection c = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = c.prepareStatement(
                Queries.getSQLSentence("TPROFESSIONALGROUPS_UPDATE"))) {     
            pst.setDouble(1, t.productivityRate);
            pst.setDouble(2, t.trienniumPayment);
            pst.setTimestamp(3, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now())); 
            pst.setLong(4, t.version + 1); 
            pst.setString(5, t.id);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Optional<ProfessionalGroupRecord> findById(String id)
            throws PersistenceException {

        Connection c = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = c.prepareStatement(
                Queries.getSQLSentence("TPROFESSIONALGROUPS_FINDBYID"))) {
            pst.setString(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                return ProfessionalGroupAssembler.toRecord(rs);
            }

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<ProfessionalGroupRecord> findAll() throws PersistenceException {
    	Connection c = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = c.prepareStatement(
                Queries.getSQLSentence("TPROFESSIONALGROUPS_FIND_ALL"))) {      
            try (ResultSet rs = pst.executeQuery()) {
                return ProfessionalGroupAssembler.toRecordList(rs);
            }

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Optional<ProfessionalGroupRecord> findByName(String name)
            throws PersistenceException {

        Connection c = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = c.prepareStatement(
                Queries.getSQLSentence("TPROFESSIONALGROUPS_FINDBYNAME"))) {
            pst.setString(1, name);
            try (ResultSet rs = pst.executeQuery()) {
                return ProfessionalGroupAssembler.toRecord(rs);
            }

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

}
