package uo.ri.cws.application.service.professionalgroup.crud.commands;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteProfessionalGroup implements Command<Void> {

	private String name;
	private ContractGateway contractGateway = Factories.persistence
			.forContract();
	private ProfessionalGroupGateway professionalGroupGateway = Factories.persistence
			.forProfessionalGroup();

	public DeleteProfessionalGroup(String name) {
		ArgumentChecks.isNotEmpty(name);
		ArgumentChecks.isNotBlank(name);
		this.name = name;
	}

	@Override
	public Void execute() throws BusinessException {
		BusinessChecks.exists(professionalGroupGateway.findByName(name),
				"El grupo profesional no se puede borrar porque no existe");
		BusinessChecks.isFalse(
				contractGateway.existsContractsForProfessionalGroup(name));
		professionalGroupGateway.remove(name);
		return null;
	}

}
