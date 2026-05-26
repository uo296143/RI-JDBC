package uo.ri.cws.application.service.professionalgroup.crud.commands;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupCrudService.ProfessionalGroupDto;
import uo.ri.cws.application.service.professionalgroup.crud.ProfessionalGroupAssembler;

public class ListAllProfessionalGroups implements Command<List<ProfessionalGroupDto>> {

    private ProfessionalGroupGateway professionalGroupGateway = Factories.persistence
        .forProfessionalGroup();

    public List<ProfessionalGroupDto> execute() {
        return professionalGroupGateway.findAll().stream().map(ProfessionalGroupAssembler::toDto).toList();
    }

}
