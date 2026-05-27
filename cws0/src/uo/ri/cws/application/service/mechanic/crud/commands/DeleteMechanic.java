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

    private MechanicGateway mechanicGateway = Factories.persistence
        .forMechanic();
    private ContractGateway contractGateway = Factories.persistence
        .forContract();
    private WorkOrderGateway workorderGateway = Factories.persistence
        .forWorkOrder();
    private InterventionGateway interventionGateway = Factories.persistence
        .forIntervention();

    public DeleteMechanic(String mechanicId) {
        ArgumentChecks.isNotNull(mechanicId);
        this.mechanicId = mechanicId;
    }

    @Override
    public Void execute() throws BusinessException {
        BusinessChecks.exists(mechanicGateway.findById(mechanicId));
        BusinessChecks.isFalse(
                workorderGateway.existsWorkordersByMechanicId(mechanicId));
        BusinessChecks
            .isFalse(interventionGateway.findByMechanicId(mechanicId));
        List<ContractRecord> contractRecord = contractGateway
            .findContractsByMechanicId(mechanicId);
        BusinessChecks.isTrue(contractRecord.isEmpty());
        Factories.persistence.forMechanic().remove(mechanicId);
        return null;
    }

}
