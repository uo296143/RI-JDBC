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
    private ContractGateway contract_gateway = Factories.persistence
        .forContract();
    private PayrollGateway payroll_gateway = Factories.persistence.forPayroll();
    private InterventionGateway intervention_gateway = Factories.persistence
        .forIntervention();

    public DeleteContract(String id) {
        ArgumentChecks.isNotEmpty(id);
        this.id = id;
    }

    /*
     * * @throws BusinessException when: - The contract does not exist, or -
     * mechanic has workorders, or - there are payrolls for this contract.
     * 
     * @throws IllegalArgumentException when - id is null or empty.
     */
    @Override
    public Void execute() throws BusinessException {

        Optional<ContractRecord> optional_contract = contract_gateway
            .findById(id);

        BusinessChecks.exists(optional_contract);

        if (intervention_gateway
            .findByMechanicId(optional_contract.get().mechanicId))
            throw new BusinessException(
                    "No se puede borrar el contrato ya que el mecánico tiene intervenciones asociadas");

        if (payroll_gateway.findByContractId(id))
            throw new BusinessException(
                    "No se puede borrar el contrato ya que tiene nominas generadas del contrato");

        contract_gateway.remove(id);
        return null;
    }

}
