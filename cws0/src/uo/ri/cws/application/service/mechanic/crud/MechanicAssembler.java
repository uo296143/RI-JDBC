package uo.ri.cws.application.service.mechanic.crud;

import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;

public class MechanicAssembler {
	
	public static MechanicRecord toRecord(MechanicDto d) {
		MechanicRecord mr = new MechanicRecord();
		mr.id = d.id;
		mr.version = d.version;
		mr.nif = d.nif;
		mr.name = d.name;
		mr.surname = d.surname;
		return mr;
	}

}
