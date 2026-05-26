package uo.ri.cws.application.service.professionalgroup.crud.commands;

import java.util.UUID;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupCrudService.ProfessionalGroupDto;
import uo.ri.cws.application.service.professionalgroup.crud.ProfessionalGroupAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class AddProfessionalGroup implements Command<ProfessionalGroupDto> {

	private ProfessionalGroupGateway professionalGroupGateway = Factories.persistence
			.forProfessionalGroup();
	private ProfessionalGroupDto professionalGroup;

	public AddProfessionalGroup(ProfessionalGroupDto arg) {
		ArgumentChecks.isNotNull(arg);
		ArgumentChecks.isNotBlank(arg.name);
		ArgumentChecks.isNotEmpty(arg.name);
		ArgumentChecks.isFalse(arg.productivityRate < 0);
		ArgumentChecks.isFalse(arg.trienniumPayment < 0);
		professionalGroup = arg;
		professionalGroup.id = UUID.randomUUID().toString();
		professionalGroup.version = 1L;
	}

	@Override
	public ProfessionalGroupDto execute() throws BusinessException {
		BusinessChecks.doesNotExist(
				professionalGroupGateway.findByName(professionalGroup.name),
				"El grupo profesional ya existe");
		professionalGroupGateway.add(ProfessionalGroupAssembler.toRecord(professionalGroup));
		return ProfessionalGroupAssembler
				.toDto(professionalGroupGateway.findById(professionalGroup.id).get());
	}

}
