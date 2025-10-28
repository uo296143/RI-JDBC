package uo.ri.cws.application.service.contracttype.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway.ContractTypeRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService.ContractTypeDto;
import uo.ri.cws.application.service.contracttype.crud.ContractTypeAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class ListContractTypeByName
        implements Command<Optional<ContractTypeDto>> {

    private String name;

    public ListContractTypeByName(String name) {
        ArgumentChecks.isNotBlank(name);
        ArgumentChecks.isNotEmpty(name);
        this.name = name;
    }

    @Override
    public Optional<ContractTypeDto> execute() throws BusinessException {
        Optional<ContractTypeRecord> optional_contract_record = Factories.persistence
            .forContractType()
            .findByName(name);
        if (optional_contract_record.isEmpty())
            return Optional.empty();
        return Optional
            .of(ContractTypeAssembler.toDto(optional_contract_record.get()));
    }

}
