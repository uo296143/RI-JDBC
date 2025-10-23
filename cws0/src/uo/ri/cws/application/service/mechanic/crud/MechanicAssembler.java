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

    public static MechanicDto toDto(MechanicRecord mr) {
        MechanicDto mdto = new MechanicDto();
        mdto.id = mr.id;
        mdto.version = mr.version;
        mdto.nif = mr.nif;
        mdto.name = mr.name;
        mdto.surname = mr.surname;
        return mdto;
    }

}
