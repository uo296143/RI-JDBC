package uo.ri.cws.application.service.contract.crud.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractSummaryDto;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class ListContractsByMechanicNif
        implements Command<List<ContractSummaryDto>> {

    private String nif;
    private ContractGateway contract_gateway = Factories.persistence
        .forContract();
    private MechanicGateway mechanic_gateway = Factories.persistence
        .forMechanic();
    private PayrollGateway payroll_gateway = Factories.persistence.forPayroll();

    public ListContractsByMechanicNif(String nif) {
        ArgumentChecks.isNotEmpty(nif);
        this.nif = nif;
    }

    /*
     * public String id;
     * 
     * public String nif;
     * 
     * // Filled in reading operations public double settlement; public String
     * state; public int numPayrolls;
     */
    @Override
    public List<ContractSummaryDto> execute() throws BusinessException {
        List<ContractSummaryDto> lista_contractSummaryDto = new ArrayList<ContractSummaryDto>();
        Optional<MechanicRecord> optional_mechanic = mechanic_gateway
            .findByNif(nif);
        if (optional_mechanic.isEmpty())
            return lista_contractSummaryDto;
        String mechanic_id = optional_mechanic.get().id;
        List<ContractRecord> lista_contractRecord = contract_gateway
            .findContractsByMechanicId(mechanic_id);
        for (ContractRecord c : lista_contractRecord) {
            ContractSummaryDto cs = new ContractSummaryDto();
            cs.id = c.id;
            cs.nif = nif;
            cs.state = c.state;
            if (cs.state.equals("TERMINATED")) {
                cs.settlement = c.settlement;
            }
            cs.numPayrolls = payroll_gateway
                .findNumberOfPayrollsByContractId(c.id);
            lista_contractSummaryDto.add(cs);
        }

        return lista_contractSummaryDto;
    }

}
