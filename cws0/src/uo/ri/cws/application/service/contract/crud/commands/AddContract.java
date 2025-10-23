package uo.ri.cws.application.service.contract.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;
import uo.ri.cws.application.service.contract.ContractCrudService;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractDto;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService.ContractTypeDto;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupCrudService;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupCrudService.ProfessionalGroupDto;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class AddContract {

    private ContractRecord contract;
    private Optional<MechanicDto> mechanic_optional;
    private Optional<ContractTypeDto> contractType_optional;
    private Optional<ProfessionalGroupDto> professionalGroup_optional;

    private ContractCrudService service_contract = Factories.service
        .forContractCrudService();
    private ContractTypeCrudService service_contractType = Factories.service
        .forContractTypeCrudService();
    private MechanicCrudService service_mechanic = Factories.service
        .forMechanicCrudService();
    private ProfessionalGroupCrudService service_professional = Factories.service
        .forProfessionalGroupCrudService();

    private ContractGateway persistence_contract = Factories.persistence
        .forContract();

    public AddContract(ContractDto contract) throws BusinessException {

        ArgumentChecks.isNotNull(contract);
        ArgumentChecks.isNotBlank(contract.mechanic.nif);
        ArgumentChecks.isNotBlank(contract.professionalGroup.name);
        ArgumentChecks.isNotNull(contract.annualBaseSalary);
        ArgumentChecks.isTrue(contract.annualBaseSalary > 0);
        ArgumentChecks.isFalse(contract.professionalGroup.equals("FIXED_TERM")
                && contract.endDate.equals(null));

        contractType_optional = service_contractType
            .findByName(contract.contractType.name);
        BusinessChecks.doesNotExist(contractType_optional);
        mechanic_optional = service_mechanic.findById(contract.mechanic.id);
        BusinessChecks.doesNotExist(mechanic_optional);
        professionalGroup_optional = service_professional
            .findByName(contract.professionalGroup.name);
        BusinessChecks.doesNotExist(professionalGroup_optional);
        if (!contract.endDate.equals(null)
                && contract.endDate.isBefore(contract.startDate)) {
            throw new BusinessException();
        }

        contract.mechanic.id = mechanic_optional.get().id;
        contract.contractType.id = contractType_optional.get().id;
        contract.professionalGroup.id = professionalGroup_optional.get().id;
    }

    public Void execute() {
        persistence_contract.add(contract);
        Console.println("Contract created");
        return null;
    }

}
