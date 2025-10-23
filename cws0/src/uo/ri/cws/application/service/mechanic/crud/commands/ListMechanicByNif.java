package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.MechanicAssembler;
import uo.ri.util.assertion.ArgumentChecks;

public class ListMechanicByNif implements Command<Optional<MechanicDto>> {

    private String nif;

    public ListMechanicByNif(String nif) {
        ArgumentChecks.isNotNull(nif);
        this.nif = nif;
    }

    @Override
    public Optional<MechanicDto> execute() {
        Optional<MechanicRecord> dto = Factories.persistence.forMechanic()
            .findByNif(nif);
        return Optional.of(MechanicAssembler.toDto(dto.get()));
    }
}
