package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.MechanicAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class ListMechanicByNif implements Command<Optional<MechanicDto>> {

    private String nif;

    public ListMechanicByNif(String nif) {
        ArgumentChecks.isNotNull(nif);
        this.nif = nif;
    }

    @Override
    public Optional<MechanicDto> execute() throws BusinessException {
        Optional<MechanicRecord> record = Factories.persistence.forMechanic()
            .findByNif(nif);
        return record.map(MechanicAssembler::toDto);
    }
    
}
