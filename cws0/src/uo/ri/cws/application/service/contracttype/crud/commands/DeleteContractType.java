package uo.ri.cws.application.service.contracttype.crud.commands;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway.ContractTypeRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteContractType implements Command<Void> {

    private String name;
    private ContractTypeGateway contractTypeGateway = Factories.persistence
        .forContractType();
    private ContractGateway contractGateway = Factories.persistence
        .forContract();

    public DeleteContractType(String name) {
        ArgumentChecks.isNotBlank(name);
        ArgumentChecks.isNotEmpty(name);
        this.name = name;
    }

    @Override
    public Void execute() throws BusinessException {
        BusinessChecks.exists(contractTypeGateway.findByName(name));
        // Se va a comporbar que no haya contratos del tipo que se quiere borrar
        List<ContractRecord> list_contracts = contractGateway.findAll();
        Optional<ContractTypeRecord> contractType;
        for (ContractRecord c : list_contracts) {
            contractType = contractTypeGateway.findById(c.contractTypeId);
            if (contractTypeGateway.findByName(contractType.get().name).get().name
                .equals(name)) {
                throw new BusinessException();
            }

        }
        contractTypeGateway.remove(name);
        return null;
    }

}
