package uo.ri.cws.application.service.contracttype.crud;

import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway.ContractTypeRecord;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractTypeOfContractDto;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService.ContractTypeDto;

public class ContractTypeAssembler {

    public static ContractTypeRecord toRecord(ContractTypeDto d) {
        ContractTypeRecord mr = new ContractTypeRecord();
        mr.id = d.id;
        mr.version = d.version;
        mr.name = d.name;
        mr.compensationDaysPerYear = d.compensationDays;
        return mr;
    }

    public static ContractTypeDto toDto(ContractTypeRecord d) {
        ContractTypeDto mr = new ContractTypeDto();
        mr.id = d.id;
        mr.version = d.version;
        mr.name = d.name;
        mr.compensationDays = d.compensationDaysPerYear;
        return mr;
    }

    public static ContractTypeOfContractDto toDtoOfContract(
            ContractTypeRecord d) {
        ContractTypeOfContractDto mr = new ContractTypeOfContractDto();
        mr.id = d.id;
        mr.name = d.name;
        mr.compensationDaysPerYear = d.compensationDaysPerYear;
        return mr;
    }

}
