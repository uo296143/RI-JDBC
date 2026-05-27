package uo.ri.cws.application.service.contract.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;
import uo.ri.cws.application.persistence.intervention.InterventionGateway;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteContract implements Command<Void> {

	private String id;
	private ContractGateway contractGateway = Factories.persistence
			.forContract();
	private PayrollGateway payrollGateway = Factories.persistence.forPayroll();
	private InterventionGateway interventionGateway = Factories.persistence
			.forIntervention();

	public DeleteContract(String id) {
		ArgumentChecks.isNotEmpty(id);
		this.id = id;
	}

	@Override
	public Void execute() throws BusinessException {

		Optional<ContractRecord> optional_contract = contractGateway
				.findById(id);

		BusinessChecks.exists(optional_contract);

		if (interventionGateway
				.findByMechanicId(optional_contract.get().mechanicId))
			throw new BusinessException(
					"No se puede borrar el contrato ya que el mecánico tiene intervenciones asociadas");

		if (payrollGateway.findNumberOfPayrollsByContractId(id) > 0)
			throw new BusinessException(
					"No se puede borrar el contrato ya que tiene nominas generadas del contrato");

		contractGateway.remove(id);
		return null;
	}

}
