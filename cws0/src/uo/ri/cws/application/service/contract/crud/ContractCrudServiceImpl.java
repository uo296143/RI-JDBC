package uo.ri.cws.application.service.contract.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.util.command.CommandExecutor;
import uo.ri.cws.application.service.contract.ContractCrudService;
import uo.ri.cws.application.service.contract.crud.commands.AddContract;
import uo.ri.cws.application.service.contract.crud.commands.DeleteContract;
import uo.ri.cws.application.service.contract.crud.commands.ListAllContracts;
import uo.ri.cws.application.service.contract.crud.commands.ListContractById;
import uo.ri.cws.application.service.contract.crud.commands.ListContractsByMechanicNif;
import uo.ri.cws.application.service.contract.crud.commands.ListInForceContracts;
import uo.ri.cws.application.service.contract.crud.commands.TerminateContract;
import uo.ri.cws.application.service.contract.crud.commands.UpdateContract;
import uo.ri.util.exception.BusinessException;

public class ContractCrudServiceImpl implements ContractCrudService {

    @Override
    public ContractDto create(ContractDto c) throws BusinessException {
        return new CommandExecutor().execute(new AddContract(c));
    }

    @Override
    public void update(ContractDto dto) throws BusinessException {
        new CommandExecutor().execute(new UpdateContract(dto));

    }

    @Override
    public void delete(String id) throws BusinessException {
        new CommandExecutor().execute(new DeleteContract(id));
    }

    @Override
    public void terminate(String contractId) throws BusinessException {
        new CommandExecutor().execute(new TerminateContract(contractId));
    }

    @Override
    public Optional<ContractDto> findById(String id) throws BusinessException {
        return new CommandExecutor().execute(new ListContractById(id));
    }

    @Override
    public List<ContractSummaryDto> findByMechanicNif(String nif)
            throws BusinessException {
        return new CommandExecutor()
            .execute(new ListContractsByMechanicNif(nif));

    }

    @Override
    public List<ContractDto> findInforceContracts() throws BusinessException {
        return new CommandExecutor().execute(new ListInForceContracts());
    }

    @Override
    public List<ContractSummaryDto> findAll() throws BusinessException {
        return new CommandExecutor().execute(new ListAllContracts());
    }

}
