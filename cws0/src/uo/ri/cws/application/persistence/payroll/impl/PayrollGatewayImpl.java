package uo.ri.cws.application.persistence.payroll.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.util.executor.Jdbc;
import uo.ri.util.jdbc.Queries;

public class PayrollGatewayImpl implements PayrollGateway {

    @Override
    public void add(PayrollRecord t) throws PersistenceException {
        // TODO Auto-generated method stub

    }

    @Override
    public void remove(String id) throws PersistenceException {
        // TODO Auto-generated method stub

    }

    @Override
    public void update(PayrollRecord t) throws PersistenceException {
        // TODO Auto-generated method stub

    }

    @Override
    public Optional<PayrollRecord> findById(String id)
            throws PersistenceException {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public List<PayrollRecord> findAll() throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public double grossSalaryOfTheLastYear(String id_contrato)
            throws PersistenceException {

        Connection c = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = c.prepareStatement(Queries
            .getSQLSentence("TPAYROLLS_GROSS_SALARY_OF_THE_LAST_YEAR"))) {
            pst.setString(1, id_contrato);
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
    public boolean findByContractId(String contract_id)
            throws PersistenceException {

        Connection c = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = c.prepareStatement(
                Queries.getSQLSentence("TPAYROLLS_FIND_BY_CONTRACT_ID"))) {
            pst.setString(1, contract_id);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public int findNumberOfPayrollsByContractId(String contract_id)
            throws PersistenceException {

        int contador = 0;
        Connection c = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = c.prepareStatement(
                Queries.getSQLSentence("TPAYROLLS_FIND_BY_CONTRACT_ID"))) {
            pst.setString(1, contract_id);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next())
                    contador += 1;
            }
            return contador;
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

}
