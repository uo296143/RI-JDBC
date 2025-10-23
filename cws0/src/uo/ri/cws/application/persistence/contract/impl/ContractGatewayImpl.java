package uo.ri.cws.application.persistence.contract.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.util.executor.Jdbc;
import uo.ri.util.jdbc.Queries;

public class ContractGatewayImpl implements ContractGateway {

    @Override
    public void add(ContractRecord t) throws PersistenceException {
        try (Connection c = Jdbc.createThreadConnection();) {
            try (PreparedStatement pst = c
                .prepareStatement(Queries.getSQLSentence("TCONTRACTS_ADD"))) {

                pst.setString(1, t.mechanicId);
                pst.setString(2, t.contractTypeId);
                pst.setString(3, t.professionalGroupId);
                pst.setDouble(4, t.annualBaseSalary);
                pst.setDate(5, Date.valueOf(t.endDate));

                pst.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(String id) throws PersistenceException {
        // TODO Auto-generated method stub

    }

    @Override
    public void update(ContractRecord t) throws PersistenceException {
        // TODO Auto-generated method stub

    }

    @Override
    public Optional<ContractRecord> findById(String id)
            throws PersistenceException {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public List<ContractRecord> findAll() throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

}
