package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.UUID;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.MechanicAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class AddMechanic implements Command<MechanicDto> {

    private MechanicGateway mechanicGateway = Factories.persistence
        .forMechanic();
    private MechanicDto m;

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

    @Override
    public MechanicDto execute() throws BusinessException {
        BusinessChecks.doesNotExist(mechanicGateway.findByNif(m.nif),
                "El mecánico ya existe");
        mechanicGateway.add(MechanicAssembler.toRecord(m));
        return MechanicAssembler.toDto(mechanicGateway.findById(m.id).get());
    }

}
