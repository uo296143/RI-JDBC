package uo.ri.cws.application.persistence.mechanic.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.util.executor.Jdbc;
import uo.ri.util.jdbc.Queries;

public class MechanicGatewayImpl implements MechanicGateway {

    @Override
    public Optional<MechanicRecord> findByNif(String nif)
            throws PersistenceException {
        Optional<MechanicRecord> result = Optional.empty();
        Connection c = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = c
            .prepareStatement(Queries.getSQLSentence("TMECHANICS_FINDBYNIF"))) {
            pst.setString(1, nif);
            try (ResultSet rs = pst.executeQuery()) {
                result = MechanicAssembler.toRecord(rs);
            }

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
        return result;
    }

    @Override
    public void add(MechanicRecord t) throws PersistenceException {
        Connection c = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = c
            .prepareStatement(Queries.getSQLSentence("TMECHANICS_ADD"))) {
            pst.setString(1, t.id);
            pst.setString(2, t.nif);
            pst.setString(3, t.name);
            pst.setString(4, t.surname);
            pst.setLong(5, t.version);
            pst.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            pst.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
            pst.setString(8, "ENABLED");
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void remove(String id) throws PersistenceException {

        Connection c = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = c
            .prepareStatement(Queries.getSQLSentence("TMECHANICS_DELETE"))) {
            pst.setString(1, id);
            pst.executeUpdate();
        }

        catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void update(MechanicRecord t) throws PersistenceException {
        Connection c = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = c
            .prepareStatement(Queries.getSQLSentence("TMECHANICS_UPDATE"))) {
            pst.setString(1, t.name);
            pst.setString(2, t.surname);
            pst.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            pst.setString(4, t.id);
            pst.executeUpdate();

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Optional<MechanicRecord> findById(String id)
            throws PersistenceException {

        Connection c = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = c
            .prepareStatement(Queries.getSQLSentence("TMECHANICS_FINDBYID"))) {
            pst.setString(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                return MechanicAssembler.toRecord(rs);
            }

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<MechanicRecord> findAll() throws PersistenceException {

        List<MechanicRecord> listOfAllMechanics = new ArrayList<MechanicRecord>();
        Connection c = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = c
            .prepareStatement(Queries.getSQLSentence("TMECHANICS_FINDALL"))) {

            try (ResultSet rs = pst.executeQuery();) {
                while (rs.next()) {
                    MechanicRecord m = new MechanicRecord();
                    m.id = rs.getString(1);
                    m.nif = rs.getString(2);
                    m.name = rs.getString(3);
                    m.surname = rs.getString(4);
                    m.version = rs.getLong(5);
                    listOfAllMechanics.add(m);
                }
            }

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }

        return listOfAllMechanics;
    }

}
