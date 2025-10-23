package uo.ri.cws.application.service.contract.crud;

import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;

public class ContractAssembler {

    public static ContractRecord toRecord(MechanicDto d) {
        ContractRecord mr = new ContractRecord();
        mr.id = d.id;
        mr.version = d.version;
        mr.nif = d.nif;
        mr.name = d.name;
        mr.surname = d.surname;
        return mr;
    }

}
