package uo.ri.cws.application.service.professionalgroup.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupCrudService.ProfessionalGroupDto;
import uo.ri.cws.application.service.professionalgroup.crud.ProfessionalGroupAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class UpdateProfessionalGroup implements Command<Void> {

    private ProfessionalGroupGateway gateway = Factories.persistence.forProfessionalGroup();
    private ProfessionalGroupRecord mr;

    public UpdateProfessionalGroup(ProfessionalGroupDto arg) {
        ArgumentChecks.isNotNull(arg);
        ArgumentChecks.isNotBlank(arg.name);
        ArgumentChecks.isNotEmpty(arg.name);
        ArgumentChecks.isFalse(arg.trienniumPayment<0);
        ArgumentChecks.isFalse(arg.productivityRate<0);
        mr = ProfessionalGroupAssembler.toRecord(arg);
    }

    public Void execute() throws BusinessException {
        ProfessionalGroupRecord readFromDatabase = checkProfessionalGroupExists(mr.id);
        BusinessChecks.hasVersion(mr.version, readFromDatabase.version,
                "Staled data");
        gateway.update(mr);
        return null;
    }

    private ProfessionalGroupRecord checkProfessionalGroupExists(String id)
            throws BusinessException {
        Optional<ProfessionalGroupRecord> optional = gateway.findById(id);
        BusinessChecks.isTrue(optional.isPresent(), "ProfessionalGroup does not exist");
        return optional.get();
    }

}
