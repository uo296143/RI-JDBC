package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.ArrayList;
import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.MechanicAssembler;

public class ListAllMechanics implements Command<List<MechanicDto>> {

    private MechanicGateway mechanic_gateway = Factories.persistence
        .forMechanic();

    public List<MechanicDto> execute() {
        List<MechanicDto> mechanics_dto = new ArrayList<MechanicDto>();
        List<MechanicRecord> mechanics_record = mechanic_gateway.findAll();
        for (MechanicRecord m : mechanics_record)
            mechanics_dto.add(MechanicAssembler.toDto(m));
        return mechanics_dto;
    }

}
