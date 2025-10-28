package uo.ri.cws.application.service.contracttype.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.util.command.CommandExecutor;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService;
import uo.ri.cws.application.service.contracttype.crud.commands.AddContractType;
import uo.ri.cws.application.service.contracttype.crud.commands.DeleteContractType;
import uo.ri.cws.application.service.contracttype.crud.commands.ListAllContractTypes;
import uo.ri.cws.application.service.contracttype.crud.commands.ListContractTypeByName;
import uo.ri.cws.application.service.contracttype.crud.commands.UpdateContractType;
import uo.ri.util.exception.BusinessException;

public class ContractTypeCrudServiceImpl implements ContractTypeCrudService {

    @Override
    public ContractTypeDto create(ContractTypeDto dto)
            throws BusinessException {
        return new CommandExecutor().execute(new AddContractType(dto));
    }

    @Override
    public void delete(String name) throws BusinessException {
        new CommandExecutor().execute(new DeleteContractType(name));

    }

    @Override
    public void update(ContractTypeDto dto) throws BusinessException {
        new CommandExecutor().execute(new UpdateContractType(dto));

    }

    @Override
    public Optional<ContractTypeDto> findByName(String name)
            throws BusinessException {
        return new CommandExecutor().execute(new ListContractTypeByName(name));
    }

    @Override
    public List<ContractTypeDto> findAll() throws BusinessException {
        return new CommandExecutor().execute(new ListAllContractTypes());
    }

}
