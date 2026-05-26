package uo.ri.cws.application.service.professionalgroup.crud;

import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupRecord;
import uo.ri.cws.application.service.contract.ContractCrudService.ProfessionalGroupOfContractDto;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupCrudService.ProfessionalGroupDto;

public class ProfessionalGroupAssembler {

    public static ProfessionalGroupOfContractDto toDtoOfProfessionalGroup(
            ProfessionalGroupRecord d) {
        ProfessionalGroupOfContractDto mr = new ProfessionalGroupOfContractDto();
        mr.id = d.id;
        mr.name = d.name;
        mr.productivityRate = d.productivityRate;
        mr.trieniumPayment = d.trienniumPayment;
        return mr;
    }
    
    public static ProfessionalGroupDto toDto(
            ProfessionalGroupRecord d) {
        ProfessionalGroupDto mr = new ProfessionalGroupDto();
        mr.id = d.id;
        mr.name = d.name;
        mr.productivityRate = d.productivityRate;
        mr.trienniumPayment = d.trienniumPayment;
        mr.version = d.version;
        return mr;
    }
    
    public static ProfessionalGroupRecord toRecord(
            ProfessionalGroupDto d) {
        ProfessionalGroupRecord mr = new ProfessionalGroupRecord();
        mr.id = d.id;
        mr.version = d.version;
        mr.name = d.name;
        mr.productivityRate = d.productivityRate;
        mr.trienniumPayment = d.trienniumPayment;
        return mr;
    }

}
