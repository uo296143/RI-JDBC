package uo.ri.cws.application.service.contract.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractDto;
import uo.ri.cws.application.service.contract.crud.ContractAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class ListContractById implements Command<Optional<ContractDto>> {

    private String id;
    private ContractGateway contractGateway = Factories.persistence
        .forContract();
    private MechanicGateway mechanicGateway = Factories.persistence
        .forMechanic();
    private ProfessionalGroupGateway pgroupGateway = Factories.persistence
        .forProfessionalGroup();
    private ContractTypeGateway contractTypeGateway = Factories.persistence
        .forContractType();

    public ListContractById(String id) {
        ArgumentChecks.isNotEmpty(id);
        this.id = id;
    }

    @Override
    public Optional<ContractDto> execute() throws BusinessException {
        Optional<ContractRecord> optional_contract_record = contractGateway
            .findById(id);
        if (optional_contract_record.isEmpty())
            return Optional.empty();
        ContractDto c = ContractAssembler.toDto(optional_contract_record.get());
        
        c.contractType = ContractAssembler.toContractTypeOfContractDto(
                contractTypeGateway.findById(c.contractType.id).get());
        c.professionalGroup = ContractAssembler
            .toProfessionalGroupOfContractDto(
                    pgroupGateway.findById(c.professionalGroup.id).get());
        c.mechanic = ContractAssembler
            .toMechanicOfContractDto(mechanicGateway.findById(c.mechanic.id).get());

        return Optional.of(c);
    }

}
