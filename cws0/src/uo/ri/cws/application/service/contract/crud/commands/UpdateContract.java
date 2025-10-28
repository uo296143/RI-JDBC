package uo.ri.cws.application.service.contract.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway.ContractTypeRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractDto;
import uo.ri.cws.application.service.contract.crud.ContractAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class UpdateContract implements Command<Void> {

    private ContractDto contract = new ContractDto();
    private ContractGateway contract_gateway = Factories.persistence
        .forContract();
    private ContractTypeGateway contractType_gateway = Factories.persistence
        .forContractType();

    public UpdateContract(ContractDto dto) {
        ArgumentChecks.isNotNull(dto);
        ArgumentChecks.isNotEmpty(dto.id);
        ArgumentChecks.isNotBlank(dto.id);
        ArgumentChecks.isTrue(dto.annualBaseSalary > 0);
        contract = dto;
        this.contract.endDate = dto.endDate;
        this.contract.annualBaseSalary = dto.annualBaseSalary;
    }

    @Override
    public Void execute() throws BusinessException {

        Optional<ContractRecord> optional_contract_record = contract_gateway
            .findById(contract.id);
        BusinessChecks.exists(optional_contract_record);
        BusinessChecks.hasVersion(contract.version,
                optional_contract_record.get().version, "Staled data");
        if (!optional_contract_record.get().state.equals("IN_FORCE"))
            throw new BusinessException("The contract is no longer in force");
        String contract_type_id = optional_contract_record.get().contractTypeId;
        Optional<ContractTypeRecord> optional_contractType_record = contractType_gateway
            .findById(contract_type_id);
        if (optional_contractType_record.get().name.equals("FIXED_TERM")
                && contract.endDate
                    .isBefore(optional_contract_record.get().startDate))
            throw new BusinessException(
                    "Contract type is FIXED_TERM and end date is earlier than startDate");
        contract_gateway.update(ContractAssembler.toRecord(contract));
        return null;
    }

}
