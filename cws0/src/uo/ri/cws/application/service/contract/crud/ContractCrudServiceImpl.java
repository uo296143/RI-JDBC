package uo.ri.cws.application.service.contract.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.service.contract.ContractCrudService;
import uo.ri.cws.application.service.contract.crud.commands.AddContract;
import uo.ri.util.exception.BusinessException;

public class ContractCrudServiceImpl implements ContractCrudService {

    @Override
    public ContractDto create(ContractDto c) throws BusinessException {
        return new AddContract(c).execute();
    }

    @Override
    public void update(ContractDto dto) throws BusinessException {
        // TODO Auto-generated method stub

    }

    @Override
    public void delete(String id) throws BusinessException {
        // TODO Auto-generated method stub

    }

    @Override
    public void terminate(String contractId) throws BusinessException {
        // TODO Auto-generated method stub

    }

    @Override
    public Optional<ContractDto> findById(String id) throws BusinessException {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public List<ContractSummaryDto> findByMechanicNif(String nif)
            throws BusinessException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ContractDto> findInforceContracts() throws BusinessException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ContractSummaryDto> findAll() throws BusinessException {
        // TODO Auto-generated method stub
        return null;
    }

}
