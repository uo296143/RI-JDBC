package uo.ri.cws.application.service.mechanic.crud.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.UUID;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.util.executor.Jdbc;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.util.assertion.*;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class AddMechanic {
	
	/**
	 * El metodo execute no va a tener parámetro.
	 */
	private static final String TMECHANICS_ADD = "insert into TMechanics"
            + "(id, nif, name, surname, version, "
            + "createdAt, updatedAt, entityState) "
            + "values (?, ?, ?, ?, ?, ?, ?, ?)";
	
	private MechanicDto m;
	
	/**
	 * 
	 */
	public AddMechanic(MechanicDto arg) {
		ArgumentChecks.isNotNull(arg);
		ArgumentChecks.isNotBlank(arg.nif);
		ArgumentChecks.isNotNull(arg.nif);
		ArgumentChecks.isNotBlank(arg.name);
		ArgumentChecks.isNotNull(arg.name);
		ArgumentChecks.isNotBlank(arg.surname);
		ArgumentChecks.isNotNull(arg.surname);
		m = arg; 
		m.id = UUID.randomUUID().toString();
        m.version = 1;
	}
	
	public MechanicDto execute() throws BusinessException {
		
	    MechanicCrudService service = Factories.service.forMechanicCrudService();
		BusinessChecks.doesNotExist(service.findByNif(m.nif));
	 
        try (Connection c = Jdbc.createThreadConnection();) {
            try (PreparedStatement pst = c.prepareStatement(TMECHANICS_ADD)) {
                pst.setString(1, m.id);
                pst.setString(2, m.nif);
                pst.setString(3, m.name);
                pst.setString(4, m.surname);
                pst.setLong(5, m.version);
                pst.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
                pst.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
                pst.setString(8, "ENABLED");               

                pst.executeUpdate();

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
		return m;
	}
}
