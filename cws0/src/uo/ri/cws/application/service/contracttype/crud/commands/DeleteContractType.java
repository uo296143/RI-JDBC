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
    private ContractTypeGateway contractType_gateway = Factories.persistence
        .forContractType();
    private ContractGateway contract_gateway = Factories.persistence
        .forContract();

    public DeleteContractType(String name) {
        ArgumentChecks.isNotBlank(name);
        ArgumentChecks.isNotEmpty(name);
        this.name = name;
    }

    @Override
    public Void execute() throws BusinessException {
        BusinessChecks.exists(contractType_gateway.findByName(name));
        // Se va a comporbar que no haya contratos del tipo que se quiere borrar
        List<ContractRecord> list_contracts = contract_gateway.findAll();
        Optional<ContractTypeRecord> contractType;
        for (ContractRecord c : list_contracts) {
            contractType = contractType_gateway.findById(c.contractTypeId);
            if (contractType_gateway.findByName(contractType.get().name)
                .equals(name)) {
                throw new BusinessException();
            }

        }
        contractType_gateway.remove(name);
        return null;
    }

}
