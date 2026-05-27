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

    private MechanicGateway mechanicGateway = Factories.persistence
        .forMechanic();

    public List<MechanicDto> execute() {
        List<MechanicDto> mechanicsDto = new ArrayList<MechanicDto>();
        List<MechanicRecord> mechanicsRecord = mechanicGateway.findAll();
        for (MechanicRecord m : mechanicsRecord)
            mechanicsDto.add(MechanicAssembler.toDto(m));
        return mechanicsDto;
    }

}
