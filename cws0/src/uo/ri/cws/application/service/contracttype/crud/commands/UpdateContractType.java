package uo.ri.cws.application.service.contracttype.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway.ContractTypeRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService.ContractTypeDto;
import uo.ri.cws.application.service.contracttype.crud.ContractTypeAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class UpdateContractType implements Command<Void> {

    private ContractTypeGateway contractType_gateway = Factories.persistence
        .forContractType();

    private ContractTypeRecord record;

    public UpdateContractType(ContractTypeDto dto) {
        ArgumentChecks.isNotNull(dto);
        ArgumentChecks.isNotEmpty(dto.name);
        ArgumentChecks.isNotBlank(dto.name);
        ArgumentChecks.isTrue(dto.compensationDays >= 0);
        this.record = ContractTypeAssembler.toRecord(dto);
    }

    @Override
    public Void execute() throws BusinessException {
        Optional<ContractTypeRecord> optional_contractType_record = contractType_gateway
            .findByName(record.name);
        BusinessChecks.exists(optional_contractType_record);
        BusinessChecks.hasVersion(record.version,
                optional_contractType_record.get().version, "Staled data");
        contractType_gateway.update(record);
        return null;
    }

}
