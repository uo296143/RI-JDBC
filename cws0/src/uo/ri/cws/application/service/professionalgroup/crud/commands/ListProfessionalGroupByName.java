package uo.ri.cws.application.service.professionalgroup.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupCrudService.ProfessionalGroupDto;
import uo.ri.cws.application.service.professionalgroup.crud.ProfessionalGroupAssembler;
import uo.ri.util.assertion.ArgumentChecks;

public class ListProfessionalGroupByName implements Command<Optional<ProfessionalGroupDto>> {

    private String name;

    public ListProfessionalGroupByName(String id) {
        ArgumentChecks.isNotEmpty(id);
        ArgumentChecks.isNotBlank(id);
        this.name = id;
    }

    public Optional<ProfessionalGroupDto> execute() {
        Optional<ProfessionalGroupRecord> optionalRecord = Factories.persistence
            .forProfessionalGroup()
            .findByName(name);

        return optionalRecord.map(ProfessionalGroupAssembler::toDto);
    }
}
