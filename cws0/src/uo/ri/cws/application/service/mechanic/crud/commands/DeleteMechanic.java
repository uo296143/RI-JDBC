package uo.ri.cws.application.service.mechanic.crud.commands;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteMechanic implements Command<Void> {

    private String mechanicId;

    private MechanicGateway mechanic_gateway = Factories.persistence
        .forMechanic();

    private ContractGateway contract_gateway = Factories.persistence
        .forContract();

    public DeleteMechanic(String mechanicId) {
        ArgumentChecks.isNotNull(mechanicId);
        this.mechanicId = mechanicId;
    }

    @Override
    public Void execute() throws BusinessException {
        BusinessChecks.exists(mechanic_gateway.findById(mechanicId));
        // BusinessChecks.doesNotExist(contract_gateway.);
        /*
         * - the mechanic has workorders assigned - the mechanic has
         * interventions done - the mechanic has contracts
         */

        Factories.persistence.forMechanic().remove(mechanicId);
        return null;
    }

}
