package uo.ri.cws.application.persistence.intervention.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.intervention.InterventionGateway;
import uo.ri.cws.application.persistence.util.executor.Jdbc;
import uo.ri.util.jdbc.Queries;

public class InterventionGatewayImpl implements InterventionGateway {

    @Override
    public void add(InterventionRecord t) throws PersistenceException {

    }

    @Override
    public void remove(String id) throws PersistenceException {

    }

    @Override
    public void update(InterventionRecord t) throws PersistenceException {      

    }

    @Override
    public Optional<InterventionRecord> findById(String id)
            throws PersistenceException {
        
        return Optional.empty();
    }

    @Override
    public List<InterventionRecord> findAll() throws PersistenceException {
        
        return null;
    }

    @Override
    public boolean findByMechanicId(String mechanicId)
            throws PersistenceException {

        Connection c = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = c.prepareStatement(
                Queries.getSQLSentence("TINTERVENTIONS_FIND_BY_MECHANIC_ID"))) {
            pst.setString(1, mechanicId);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

}
