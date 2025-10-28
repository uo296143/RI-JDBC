package uo.ri.cws.application.service.contract.crud.commands;

import java.util.ArrayList;
import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractDto;
import uo.ri.cws.application.service.contract.crud.ContractAssembler;
import uo.ri.util.exception.BusinessException;

public class ListInForceContracts implements Command<List<ContractDto>> {

    private ContractGateway contract_gateway = Factories.persistence
        .forContract();

    @Override
    public List<ContractDto> execute() throws BusinessException {
        List<ContractRecord> lista_record = contract_gateway.findAll();
        List<ContractDto> lista_inForce = new ArrayList<ContractDto>();
        for (ContractRecord r : lista_record) {
            if (r.state.equals("IN_FORCE"))
                lista_inForce.add(ContractAssembler.toDto(r));
        }
        return lista_inForce;
    }

}
