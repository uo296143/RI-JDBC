package uo.ri.cws.application.service.professionalgroup.crud;

import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupRecord;
import uo.ri.cws.application.service.contract.ContractCrudService.ProfessionalGroupOfContractDto;

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

}
