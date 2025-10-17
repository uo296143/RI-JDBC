package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteMechanic {

	private String mechanicId;
	/**
	 * MechanicGateway
	 * WorkOrder
	 * IntervetnionGateway
	 */
	private static final String TMECHANICS_DELETE = "DELETE FROM TMECHANICS "
	            + "WHERE ID = ?";
	
	public DeleteMechanic(String mechanicId) {
		ArgumentChecks.isNotNull(mechanicId);
		this.mechanicId = mechanicId;
	}
	

	public Void execute() throws BusinessException {
		MechanicRecord readFromDatabase = checkMechanicExists(mechanicId); // Se hace la comprobacion en 2 sitios.
		// BusinessChecks.hasVersion(m.version, readFromDatabase.version, "Staled data");
		Factories.persistence.forMechanic().remove(mechanicId);
		return null;
	}
	
	private MechanicRecord checkMechanicExists(String id) throws BusinessException{
		Optional<MechanicRecord> optional = Factories.persistence.forMechanic().findById(id);
		BusinessChecks.isTrue(optional.isPresent(), "Mechanic does not exist");
		return optional.get();
	}

//	public void execute() throws BusinessException {
//	    MechanicCrudService service = Factories.service.forMechanicCrudService();
//    	BusinessChecks.exists(service.findById(mechanicId), "The mechanic couldn´t be deleted because he doesn´t exist.");
//
//		
//        try (Connection c = Jdbc.createThreadConnection();) {
//            try (PreparedStatement pst = c
//                    .prepareStatement(TMECHANICS_DELETE)) {
//                pst.setString(1, mechanicId);
//                pst.executeUpdate();
//            }
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//	}

}
