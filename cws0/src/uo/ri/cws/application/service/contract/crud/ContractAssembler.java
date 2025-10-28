package uo.ri.cws.application.service.contract.crud;

import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway.ContractTypeRecord;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupRecord;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractDto;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractTypeOfContractDto;
import uo.ri.cws.application.service.contract.ContractCrudService.MechanicOfContractDto;
import uo.ri.cws.application.service.contract.ContractCrudService.ProfessionalGroupOfContractDto;

public class ContractAssembler {

    public static ContractRecord toRecord(ContractDto d) {
        ContractRecord mr = new ContractRecord();
        mr.id = d.id;
        mr.version = d.version;
        mr.annualBaseSalary = d.annualBaseSalary;
        mr.contractTypeId = d.contractType.id;
        mr.mechanicId = d.mechanic.id;
        mr.professionalGroupId = d.professionalGroup.id;
        mr.startDate = d.startDate;
        mr.endDate = d.endDate;
        mr.taxRate = d.taxRate;
        mr.settlement = d.settlement;
        mr.state = d.state;
        return mr;
    }

    public static ContractDto toDto(ContractRecord d) {
        ContractDto mr = new ContractDto();
        mr.id = d.id;
        mr.version = d.version;
        mr.annualBaseSalary = d.annualBaseSalary;
        mr.contractType.id = d.contractTypeId;
        mr.mechanic.id = d.mechanicId;

        mr.professionalGroup.id = d.professionalGroupId;
        mr.startDate = d.startDate;
        mr.endDate = d.endDate;
        mr.taxRate = d.taxRate;
        mr.settlement = d.settlement;
        mr.state = d.state;
        return mr;
    }

    public static ContractTypeOfContractDto toContractTypeOfContractDto(
            ContractTypeRecord contractTypeRecord) {
        ContractTypeOfContractDto c = new ContractTypeOfContractDto();
        c.id = contractTypeRecord.id;
        c.compensationDaysPerYear = contractTypeRecord.compensationDaysPerYear;
        c.name = contractTypeRecord.name;
        return c;
    }

    public static ProfessionalGroupOfContractDto toProfessionalGroupOfContractDto(
            ProfessionalGroupRecord professionalGroupRecord) {
        ProfessionalGroupOfContractDto c = new ProfessionalGroupOfContractDto();
        c.id = professionalGroupRecord.id;
        c.name = professionalGroupRecord.name;
        c.productivityRate = professionalGroupRecord.productivityRate;
        c.trieniumPayment = professionalGroupRecord.trienniumPayment;
        return c;
    }

    public static MechanicOfContractDto toMechanicOfContractDto(
            MechanicRecord m) {
        MechanicOfContractDto c = new MechanicOfContractDto();
        c.id = m.id;
        c.name = m.name;
        c.nif = m.nif;
        c.surname = m.surname;
        return c;
    }

}
