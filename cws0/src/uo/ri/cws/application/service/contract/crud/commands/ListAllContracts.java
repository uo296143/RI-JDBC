package uo.ri.cws.application.service.contract.crud.commands;

import java.util.ArrayList;
import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractSummaryDto;
import uo.ri.util.exception.BusinessException;

public class ListAllContracts implements Command<List<ContractSummaryDto>> {

    private ContractGateway contract_gateway = Factories.persistence
        .forContract();
    private PayrollGateway payroll_gateway = Factories.persistence.forPayroll();
    private MechanicGateway mechanic_gateway = Factories.persistence
        .forMechanic();

    @Override
    public List<ContractSummaryDto> execute() throws BusinessException {
        List<ContractSummaryDto> lista_contractSummaryDto = new ArrayList<ContractSummaryDto>();
        List<ContractRecord> lista_contractRecord = contract_gateway.findAll();
        for (ContractRecord c : lista_contractRecord) {
            ContractSummaryDto cs = new ContractSummaryDto();
            cs.id = c.id;
            cs.nif = mechanic_gateway.findById(c.mechanicId).get().nif;
            cs.state = c.state;
            if (cs.state.equals("TERMINATED"))
                cs.settlement = c.settlement;
            cs.numPayrolls = payroll_gateway
                .findNumberOfPayrollsByContractId(c.id);
            lista_contractSummaryDto.add(cs);
        }
        return lista_contractSummaryDto;
    }
}
