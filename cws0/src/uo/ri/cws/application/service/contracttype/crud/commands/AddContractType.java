package uo.ri.cws.application.service.contracttype.crud.commands;

import java.util.UUID;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway.ContractTypeRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService.ContractTypeDto;
import uo.ri.cws.application.service.contracttype.crud.ContractTypeAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class AddContractType implements Command<ContractTypeDto> {

    private ContractTypeRecord record;
    private ContractTypeGateway ct_gateway = Factories.persistence
        .forContractType();

    public AddContractType(ContractTypeDto dto) {
        ArgumentChecks.isNotNull(dto);
        ArgumentChecks.isNotEmpty(dto.name);
        ArgumentChecks.isNotBlank(dto.name);
        ArgumentChecks.isTrue(dto.compensationDays >= 0);
        this.record = ContractTypeAssembler.toRecord(dto);
        record.id = UUID.randomUUID().toString();
        record.version = 1L;
    }

    @Override
    public ContractTypeDto execute() throws BusinessException {
        BusinessChecks.doesNotExist(ct_gateway.findByName(record.name));
        ct_gateway.add(record);
        return ContractTypeAssembler
            .toDto(ct_gateway.findById(record.id).get());
    }

}
