package uo.ri.cws.application.persistence.mechanic.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.util.executor.Jdbc;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.util.jdbc.Queries;

public class MechanicGatewayImpl implements MechanicGateway {

	@Override
	public Optional<MechanicRecord> findByNif(String nif) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public void add(MechanicRecord t) throws PersistenceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(String id) throws PersistenceException {
	  try (Connection c = Jdbc.createThreadConnection();) {
	        try (PreparedStatement pst = c
	                .prepareStatement(Queries.getSQLSentence("TMECHANICS_DELETE"))) {
	            pst.setString(1, id);
	            pst.executeUpdate();
	        }
	
	    } catch (SQLException e) {
	        throw new RuntimeException(e);
	    }
	}

	@Override
	public void update(MechanicRecord t) throws PersistenceException {
		try  {
			Connection c = Jdbc.getCurrentConnection();
			try (PreparedStatement pst = c.prepareStatement(Queries.getSQLSentence("TMECHANICS_UPDATE"))) {
				pst.setString(1, t.name);
				pst.setString(2, t.surname);
				pst.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
				pst.setString(4, t.id);

				pst.executeUpdate();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Optional<MechanicRecord> findById(String id)
			throws PersistenceException {
		 Optional<MechanicDto> result = Optional.empty();
		 MechanicDto m = new MechanicDto();
		 try (Connection c = Jdbc.createThreadConnection()) {
	            try (PreparedStatement pst = c
	                    .prepareStatement(Queries.getSQLSentence("TMECHANICS_FINDBYID"))) {
	                pst.setString(1, id);
	                try (ResultSet rs = pst.executeQuery()) {
	                    if (rs.next()) {
	                       
	                        result = Optional.of(m);
	                    }
	                }
	            }
	        } catch (SQLException e) {
	            throw new PersistenceException(e);
	        }
		 return result.emptyOf(m);
	}

	@Override
	public List<MechanicRecord> findAll() throws PersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

}
