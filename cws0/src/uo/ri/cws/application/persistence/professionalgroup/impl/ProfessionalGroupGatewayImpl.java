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
        // TODO Auto-generated method stub

    }

    @Override
    public void remove(String id) throws PersistenceException {
        // TODO Auto-generated method stub

    }

    @Override
    public void update(ProfessionalGroupRecord t) throws PersistenceException {
        // TODO Auto-generated method stub

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
        // TODO Auto-generated method stub
        return null;
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
