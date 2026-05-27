package uo.ri.cws.application.service.invoice.create.commands;

import java.util.ArrayList;
import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderRecord;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingWorkOrderDto;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindNotInvoicedWorkOrdersByClientNif
        implements Command<List<InvoicingWorkOrderDto>> {

    private final WorkOrderGateway workOrderGateway = Factories.persistence
        .forWorkOrder();

    private String nif;

    public FindNotInvoicedWorkOrdersByClientNif(String nif) {
        ArgumentChecks.isNotNull(nif);
        this.nif = nif;
    }

    @Override
    public List<InvoicingWorkOrderDto> execute() throws BusinessException {

        List<InvoicingWorkOrderDto> invoicingWOs = new ArrayList<InvoicingWorkOrderDto>();
        List<WorkOrderRecord> workOrders = workOrderGateway
            .findNotInvoicedWorkOrdersByClientNif(nif);
        for (WorkOrderRecord w : workOrders) {
            InvoicingWorkOrderDto i = new InvoicingWorkOrderDto();
            i.id = w.id;
            i.description = w.description;
            i.date = w.date.atStartOfDay();
            i.state = w.state;
            i.amount = w.amount;
            invoicingWOs.add(i);
        }

        return invoicingWOs;
    }
}
