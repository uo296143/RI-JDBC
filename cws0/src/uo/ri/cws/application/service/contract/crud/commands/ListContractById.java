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
import uo.ri.cws.application.service.contracttype.crud.ContractTypeAssembler;
import uo.ri.cws.application.service.mechanic.crud.MechanicAssembler;
import uo.ri.cws.application.service.professionalgroup.crud.ProfessionalGroupAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class ListContractById implements Command<Optional<ContractDto>> {

    private String id;
    private ContractGateway contract_gateway = Factories.persistence
        .forContract();
    private MechanicGateway mechanic_gateway = Factories.persistence
        .forMechanic();
    private ProfessionalGroupGateway pgroup_gateway = Factories.persistence
        .forProfessionalGroup();
    private ContractTypeGateway contractType_gateway = Factories.persistence
        .forContractType();

    public ListContractById(String id) {
        ArgumentChecks.isNotEmpty(id);
        this.id = id;
    }

    @Override
    public Optional<ContractDto> execute() throws BusinessException {
        Optional<ContractRecord> optional_contract_record = contract_gateway
            .findById(id);
        if (optional_contract_record.isEmpty())
            return Optional.empty();
        ContractDto c = ContractAssembler.toDto(optional_contract_record.get());
        // Recupero la informacion completa de mechanic, contractType y
        // professionalGroup
        c.contractType = ContractTypeAssembler.toDtoOfContract(
                contractType_gateway.findById(c.contractType.id).get());
        c.professionalGroup = ProfessionalGroupAssembler
            .toDtoOfProfessionalGroup(
                    pgroup_gateway.findById(c.professionalGroup.id).get());
        c.mechanic = MechanicAssembler
            .toDtoOfContract(mechanic_gateway.findById(c.mechanic.id).get());

        return Optional.of(c);
    }

}
