package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;
import uo.ri.cws.application.persistence.intervention.InterventionGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteMechanic implements Command<Void> {

    private String mechanicId;

    private MechanicGateway mechanic_gateway = Factories.persistence
        .forMechanic();
    private ContractGateway contract_gateway = Factories.persistence
        .forContract();
    private WorkOrderGateway workorder_gateway = Factories.persistence
        .forWorkOrder();
    private InterventionGateway intervention_gateway = Factories.persistence
        .forIntervention();

    public DeleteMechanic(String mechanicId) {
        ArgumentChecks.isNotNull(mechanicId);
        this.mechanicId = mechanicId;
    }

    @Override
    public Void execute() throws BusinessException {
        BusinessChecks.exists(mechanic_gateway.findById(mechanicId));
        BusinessChecks.doesNotExist(
                workorder_gateway.findWorkordersByMechanicId(mechanicId));
        BusinessChecks
            .isFalse(intervention_gateway.findByMechanicId(mechanicId));
        List<ContractRecord> contract_record = contract_gateway
            .findContractsByMechanicId(mechanicId);
        BusinessChecks.isTrue(contract_record.isEmpty());
        Factories.persistence.forMechanic().remove(mechanicId);
        return null;
    }

}
