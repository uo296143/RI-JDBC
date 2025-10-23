package uo.ri.cws.application.service.contracttype.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.service.contracttype.ContractTypeCrudService;
import uo.ri.util.exception.BusinessException;

public class ContractTypeCrudServiceImpl implements ContractTypeCrudService {

    @Override
    public ContractTypeDto create(ContractTypeDto dto)
            throws BusinessException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void delete(String name) throws BusinessException {
        // TODO Auto-generated method stub

    }

    @Override
    public void update(ContractTypeDto dto) throws BusinessException {
        // TODO Auto-generated method stub

    }

    @Override
    public Optional<ContractTypeDto> findByName(String name)
            throws BusinessException {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public List<ContractTypeDto> findAll() throws BusinessException {
        // TODO Auto-generated method stub
        return null;
    }

}
