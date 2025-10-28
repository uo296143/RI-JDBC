package uo.ri.cws.application.service.contracttype.crud.commands;

import java.util.ArrayList;
import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway.ContractTypeRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService.ContractTypeDto;
import uo.ri.cws.application.service.contracttype.crud.ContractTypeAssembler;
import uo.ri.util.exception.BusinessException;

public class ListAllContractTypes implements Command<List<ContractTypeDto>> {

    @Override
    public List<ContractTypeDto> execute() throws BusinessException {
        List<ContractTypeDto> list_dto = new ArrayList<ContractTypeDto>();
        List<ContractTypeRecord> list_record = Factories.persistence
            .forContractType()
            .findAll();
        for (ContractTypeRecord r : list_record) {
            list_dto.add(ContractTypeAssembler.toDto(r));
        }
        return list_dto;
    }

}
