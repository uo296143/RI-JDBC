package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.MechanicAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class UpdateMechanic implements Command<Void> {

    private MechanicGateway gateway = Factories.persistence.forMechanic();
    private MechanicRecord mr;

    public UpdateMechanic(MechanicDto arg) {
        ArgumentChecks.isNotNull(arg);
        ArgumentChecks.isNotBlank(arg.id);
        ArgumentChecks.isNotBlank(arg.name);
        ArgumentChecks.isNotBlank(arg.surname);
        ArgumentChecks.isNotNull(arg.nif);
        mr = MechanicAssembler.toRecord(arg);
    }

    public Void execute() throws BusinessException {
        MechanicRecord readFromDatabase = checkMechanicExists(mr.id);
        BusinessChecks.hasVersion(mr.version, readFromDatabase.version,
                "Staled data");
        gateway.update(mr);
        return null;
    }

    private MechanicRecord checkMechanicExists(String id)
            throws BusinessException {
        Optional<MechanicRecord> optional = gateway.findById(id);
        BusinessChecks.isTrue(optional.isPresent(), "Mechanic does not exist");
        return optional.get();
    }

}
