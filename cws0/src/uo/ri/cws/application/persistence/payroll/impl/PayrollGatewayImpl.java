package uo.ri.cws.application.persistence.payroll.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.util.executor.Jdbc;
import uo.ri.util.jdbc.Queries;

public class PayrollGatewayImpl implements PayrollGateway {

	@Override
	public void add(PayrollRecord t) throws PersistenceException {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(Queries.getSQLSentence("TPAYROLLS_ADD"))) {	       
	        pst.setString(1, t.id);
	        pst.setLong(2, t.version);
	        pst.setTimestamp(3, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now())); 
	        pst.setTimestamp(4, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
	        pst.setString(5, "ACTIVE"); 	        	        
	        pst.setDouble(6, t.baseSalary);
	        pst.setDouble(7, t.extraSalary);
	        pst.setDouble(8, t.productivityEarning);
	        pst.setDouble(9, t.trienniumEarning);	        	        
	        pst.setDouble(10, t.taxDeduction);
	        pst.setDouble(11, t.nicDeduction);	        	        
	        pst.setDate(12, java.sql.Date.valueOf(t.date)); 
	        pst.setString(13, t.contractId);               	        	       
	        pst.executeUpdate();
	    } catch (SQLException e) {
	        throw new PersistenceException(e);
	    }
	}

    @Override
    public void remove(String id) throws PersistenceException {
    	Connection c = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = c.prepareStatement(
                Queries.getSQLSentence("TPAYROLLS_REMOVE"))) {
            pst.setString(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void update(PayrollRecord t) throws PersistenceException {

    }

    @Override
    public Optional<PayrollRecord> findById(String id)
            throws PersistenceException {

        Connection c = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = c.prepareStatement(
                Queries.getSQLSentence("TPAYROLLS_FIND_BY_ID"))) {
            pst.setString(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                return PayrollAssembler.toRecord(rs);
            }

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<PayrollRecord> findAll() throws PersistenceException {
    	
        Connection c = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = c.prepareStatement(
                Queries.getSQLSentence("TPAYROLLS_FIND_ALL"))) {
            try (ResultSet rs = pst.executeQuery()) {
                return PayrollAssembler.toRecordList(rs);
            }

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }

    }

    @Override
    public double grossSalaryOfTheLastYear(String idContrato, LocalDate fechaAlta, LocalDate fechaBaja)
            throws PersistenceException {

        Connection c = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = c.prepareStatement(Queries
            .getSQLSentence("TPAYROLLS_GROSS_SALARY_OF_THE_LAST_YEAR"))) {
            pst.setString(1, idContrato);
            pst.setDate(2, java.sql.Date.valueOf(fechaAlta)); 
            pst.setDate(3, java.sql.Date.valueOf(fechaBaja));
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next())
                    return rs.getDouble(1);
                return 0;
            }

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public int findNumberOfPayrollsByContractId(String contractId)
            throws PersistenceException {

        int contador = 0;
        Connection c = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = c.prepareStatement(
                Queries.getSQLSentence("TPAYROLLS_FIND_BY_CONTRACT_ID"))) {
            pst.setString(1, contractId);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next())
                    contador += 1;
            }
            return contador;
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

	@Override
	public boolean existsPayrollForContractInDate(String contractId,
			LocalDate finMesAnterior) {
		Connection c = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = c.prepareStatement(
                Queries.getSQLSentence("TPAYROLLS_EXISTS_PAYROLL_FOR_CONTRACT_IN_DATE"))) {
        	pst.setDate(1, Date.valueOf(finMesAnterior));
            pst.setString(2, contractId);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
	}

	@Override
	public int deletePayrollsOf(LocalDate inicioMesAnterior, LocalDate finMesAnterior) {
		Connection c = Jdbc.getCurrentConnection();
		int filasAfectadas;
        try (PreparedStatement pst = c
            .prepareStatement(Queries.getSQLSentence("TPAYROLLS_REMOVE_DATE_OF"))) {
            pst.setDate(1, Date.valueOf(inicioMesAnterior));
            pst.setDate(2, Date.valueOf(finMesAnterior));
            filasAfectadas = pst.executeUpdate();
        }
        
        catch (SQLException e) {
            throw new PersistenceException(e);
        }
        
        return filasAfectadas;
	}

	@Override
	public Void deleteLastPayrollOfMechanicId(String mechanicId, LocalDate inicioMesAnterior, LocalDate finMesAnterior) {
		Connection c = Jdbc.getCurrentConnection();
		
        try (PreparedStatement pst = c
            .prepareStatement(Queries.getSQLSentence("TPAYROLLS_REMOVE_LAST_OF_MECHANIC"))) {
        	pst.setString(1, mechanicId);
        	pst.setDate(2, Date.valueOf(inicioMesAnterior));
        	pst.setDate(3, Date.valueOf(finMesAnterior));
            pst.executeUpdate();
        }
        
        catch (SQLException e) {
            throw new PersistenceException(e);
        }
        
        return null;
	}

	@Override
	public List<PayrollRecord> findPayrollsByMechanicId(
			String mechanicId) {
		
		Connection c = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = c.prepareStatement(
                Queries.getSQLSentence("TPAYROLLS_FIND_BY_MECHANIC_ID"))) {
            pst.setString(1, mechanicId);
            try (ResultSet rs = pst.executeQuery()) {
                return PayrollAssembler.toRecordList(rs);
            }

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
	}

	@Override
	public List<PayrollRecord> findPayrollsByProfessionalGroup(
			String name) {
		
		Connection c = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = c.prepareStatement(
                Queries.getSQLSentence("TPAYROLLS_FIND_BY_PROFESSIONALGROUP_NAME"))) {
            pst.setString(1, name);
            try (ResultSet rs = pst.executeQuery()) {
                return PayrollAssembler.toRecordList(rs);
            }

        } catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

}
