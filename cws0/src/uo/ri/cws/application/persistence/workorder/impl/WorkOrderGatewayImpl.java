package uo.ri.cws.application.persistence.workorder.impl;

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
import uo.ri.cws.application.persistence.util.executor.Jdbc;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.util.jdbc.Queries;

public class WorkOrderGatewayImpl implements WorkOrderGateway {

	@Override
	public void add(WorkOrderRecord t) throws PersistenceException {

	}

	@Override
	public void remove(String id) throws PersistenceException {

	}

	@Override
	public void update(WorkOrderRecord t) throws PersistenceException {
		Connection c = Jdbc.getCurrentConnection();
		try (PreparedStatement pst = c.prepareStatement(
				Queries.getSQLSentence("TWORKORDERS_UPDATE"))) {
			pst.setString(1, t.description);
			pst.setString(2, t.state);
			pst.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			pst.setString(4, t.mechanicId);
			pst.setString(5, t.vehicleId);
			pst.setDouble(6, t.amount);
			pst.setString(7, t.id);
			pst.executeUpdate();

		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public Optional<WorkOrderRecord> findById(String id) {
		Connection c = Jdbc.getCurrentConnection();
		try (PreparedStatement pst = c.prepareStatement(
				Queries.getSQLSentence("TWORKORDERS_FIND_BY_ID"))) {
			pst.setString(1, id);
			try (ResultSet rs = pst.executeQuery()) {
				return WorkOrderAssembler.toRecord(rs);
			}
		} catch (SQLException e) {
			throw new PersistenceException();
		}
	}

	@Override
	public List<WorkOrderRecord> findAll() throws PersistenceException {
		return null;
	}

	@Override
	public boolean existsWorkordersByMechanicId(
			String mechanicId) throws PersistenceException {

		Connection connection = Jdbc.getCurrentConnection();
		try (PreparedStatement pst = connection.prepareStatement(
				Queries.getSQLSentence("TWORKORDERS_EXISTS_WORKORDERS_FOR_MECHANIC_ID"))) {
			pst.setString(1, mechanicId);
			try (ResultSet rs = pst.executeQuery()) {
				return rs.next();
			}
		} catch (SQLException e) {
			throw new PersistenceException();
		}
	
	}

	@Override
	public List<WorkOrderRecord> findNotInvoicedWorkOrdersByClientNif(
			String clientNif) throws PersistenceException {
		List<WorkOrderRecord> invoicingWOs = new ArrayList<WorkOrderRecord>();

		Connection c = Jdbc.getCurrentConnection();
		try (PreparedStatement pst = c.prepareStatement(
				Queries.getSQLSentence("TWORKORDERS_FIND_NOT_INVOICED"))) {
			pst.setString(1, clientNif);
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

	@Override
	public double findWorkOrdersTotalAmountByMechanicIdInDate(String mechanicId,
			LocalDate start, LocalDate end) {
		double totalAmount = 0.0;
		Connection connection = Jdbc.getCurrentConnection();
		try (PreparedStatement pst = connection.prepareStatement(
				Queries.getSQLSentence("TWORKORDERS_FIND_AMOUNT_BETWEEN"))) {
			pst.setDate(1, Date.valueOf(start));
			pst.setDate(2, Date.valueOf(end));
			pst.setString(3, mechanicId);
			try (ResultSet rs = pst.executeQuery()) {
				if (rs.next()) {
					return rs.getDouble(1);
				}
			}
		} catch (SQLException e) {
			throw new PersistenceException();
		}

		return totalAmount;

	}

}
