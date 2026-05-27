package uo.ri.cws.application.service.invoice.create.commands;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderRecord;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.service.invoice.create.InvoiceAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.math.Rounds;

/**
 * Create an invoice by one or more workorders of the same client on FINISHED
 * state and not already INVOICED
 */
public class CreateInvoice implements Command<InvoiceDto> {

    private final InvoiceGateway invoiceGateway = Factories.persistence.forInvoice();
    private final WorkOrderGateway workOrderGateway = Factories.persistence.forWorkOrder();
    private final List<String> workOrdersIds;

    /**
     * @param workOrderIds list of the workorders to invoice
     */
    public CreateInvoice(List<String> workOrderIds) {
        ArgumentChecks.isNotNull(workOrderIds, "Workorders's list is null");
        ArgumentChecks.isFalse(workOrderIds.isEmpty(), "Workorder's list is empty");
        for (String id : workOrderIds) {
            ArgumentChecks.isNotNull(id, "A workorder id is null");
        }
        this.workOrdersIds = new ArrayList<>(workOrderIds); // Defensiva copy
    }

    @Override
    public InvoiceDto execute() throws BusinessException {
        
        List<WorkOrderRecord> workOrders = fetchWorkOrders(workOrdersIds);

        BusinessChecks.isTrue(workOrders.size() == workOrdersIds.size(), "Some workorder does not exist");
        BusinessChecks.isTrue(areAllWorkOrdersFinished(workOrders), "Some workorder is not finished yet");
        
        InvoiceDto invoice = createInvoice(workOrders);
        updateWorkOrdersToInvoiced(workOrders, invoice.id);

        return InvoiceAssembler.toDto(invoiceGateway.findById(invoice.id).get());
    }

    private List<WorkOrderRecord> fetchWorkOrders(List<String> ids) {
        List<WorkOrderRecord> result = new ArrayList<>();
        for (String id : ids) {
            workOrderGateway.findById(id).ifPresent(result::add);
        }
        return result;
    }

    private boolean areAllWorkOrdersFinished(List<WorkOrderRecord> workOrders) {
        for (WorkOrderRecord wo : workOrders) {
            if (!"FINISHED".equals(wo.state)) {
                return false;
            }
        }
        return true;
    }

    private void updateWorkOrdersToInvoiced(List<WorkOrderRecord> workOrders, String invoiceId) {
        for (WorkOrderRecord wo : workOrders) {
            wo.invoiceId = invoiceId;
            wo.state = "INVOICED";
            workOrderGateway.update(wo);
        }
    }

    private InvoiceDto createInvoice(List<WorkOrderRecord> workOrders) {
        InvoiceDto dto = new InvoiceDto();
        dto.id = UUID.randomUUID().toString();
        dto.version = 1L;
        dto.number = invoiceGateway.findNextNumber();
        dto.date = LocalDate.now();
        dto.state = "NOT_YET_PAID";

        double amountWithoutVat = calculateTotalInvoice(workOrders);
        double vatTax = vatPercentage(dto.date);
        double vatAmount = amountWithoutVat * (vatTax / 100.0);
        
        dto.vat = vatAmount;
        dto.amount = Rounds.toCents(amountWithoutVat + vatAmount);

        invoiceGateway.add(InvoiceAssembler.toRecord(dto));
        return dto;
    }

    private double calculateTotalInvoice(List<WorkOrderRecord> workOrders) {
        double total = 0.0;
        for (WorkOrderRecord wo : workOrders) {
            total += wo.amount;
        }
        return total;
    }

    private double vatPercentage(LocalDate date) {
        return LocalDate.parse("2012-07-01").isBefore(date) ? 21.0 : 18.0;
    }
}