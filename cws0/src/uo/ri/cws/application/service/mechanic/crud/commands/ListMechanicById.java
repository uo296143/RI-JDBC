package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.MechanicAssembler;
import uo.ri.util.assertion.ArgumentChecks;

public class ListMechanicById implements Command<Optional<MechanicDto>> {

    private String id;

    public ListMechanicById(String id) {
        ArgumentChecks.isNotNull(id);
        this.id = id;
    }

    public Optional<MechanicDto> execute() {
        Optional<MechanicRecord> optional_record = Factories.persistence
            .forMechanic()
            .findById(id);

        return optional_record.map(MechanicAssembler::toDto);
    }
}
