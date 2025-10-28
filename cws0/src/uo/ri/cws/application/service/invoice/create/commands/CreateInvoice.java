package uo.ri.cws.application.service.invoice.create.commands;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
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

    // Gateways to access the database of different tables
    private final InvoiceGateway invoice_gateway = Factories.persistence
        .forInvoice();
    private final WorkOrderGateway workOrder_gateway = Factories.persistence
        .forWorkOrder();

    // List of the workOrders id
    private List<String> workOrdersIds;

    /**
     * 
     * @param workOrderIds, list of the workorders to invoice
     */
    public CreateInvoice(List<String> workOrderIds) {
        ArgumentChecks.isNotNull(workOrderIds, "Workorders`s list is null");
        ArgumentChecks.isFalse(workOrderIds.isEmpty(),
                "Workorder´s list is empty");
        for (String id : workOrderIds)
            ArgumentChecks.isNotNull(id, "A workorder id is null");
        this.workOrdersIds = workOrderIds;
    }

    /**
     * Create an invoice
     * 
     * @throws BusinessException if any business rule is violated
     */
    @Override
    public InvoiceDto execute() throws BusinessException {
        BusinessChecks.isTrue(checkWorkOrdersExist(workOrdersIds),
                "Some workorder does not exist");
        BusinessChecks.isTrue(checkWorkOrdersFinished(workOrdersIds),
                "Some workorder is not finished yet");

        InvoiceDto invoice = createInvoice();
        linkWorkordersToInvoice(invoice.id, workOrdersIds);
        markWorkOrderAsInvoiced(workOrdersIds);
        updateVersion(workOrdersIds);
        updateTimeVersion(workOrdersIds);

        return InvoiceAssembler
            .toDto(invoice_gateway.findById(invoice.id).get());
    }

    /*
     * If any of the workOrders doesn´t exist return false
     */
    private boolean checkWorkOrdersExist(List<String> workOrdersIDS) {
        for (String id : workOrdersIDS)
            if (workOrder_gateway.findById(id).isEmpty())
                return false;
        return true;
    }

    /*
     * If any of the workOrders is not in FINISHED state return false
     */
    private boolean checkWorkOrdersFinished(List<String> workOrderIDs) {
        for (String id : workOrderIDs)
            if (!workOrder_gateway.findStatusById(id).equals("FINISHED"))
                return false;
        return true;
    }

    /*
     * Generates next invoice number (not to be confused with the inner id)
     */
    private long generateInvoiceNumber() {
        return invoice_gateway.findNextNumber();
    }

    /*
     * Compute total amount of the invoice (as the total of individual work
     * orders' amount
     */
    private double calculateTotalInvoice(List<String> workOrderIDs) {
        double total = 0.0;
        for (String id : workOrderIDs) {
            total += workOrder_gateway.findAmountById(id);
        }
        return total;
    }

    /*
     * returns vat percentage
     */
    private double vatPercentage(LocalDate d) {
        return LocalDate.parse("2012-07-01").isBefore(d) ? 21.0 : 18.0;
    }

    /*
     * Creates the invoice in the database Returns the id. Cambiado, ahora
     * devuelve InvoiceDto para que pase las pruebas.
     */
    private InvoiceDto createInvoice() {
        InvoiceDto dto = new InvoiceDto();
        dto.id = UUID.randomUUID().toString();
        dto.version = 1L;
        dto.number = generateInvoiceNumber();
        dto.date = LocalDate.now();
        double amount_withoutvat = calculateTotalInvoice(workOrdersIds);
        double vat = vatPercentage(dto.date);
        double vatAmount = amount_withoutvat * (vat / 100); // vat amount
        double total = amount_withoutvat + vatAmount; // vat included
        dto.vat = vatAmount;
        dto.state = "NOT_YET_PAID";
        total = Rounds.toCents(total);
        dto.amount = total;
        invoice_gateway.add(InvoiceAssembler.toRecord(dto));
        return dto;
    }

    private void updateTimeVersion(List<String> workOrderIds) {
        for (String workOrderID : workOrderIds) {
            workOrder_gateway.updateTimeStamp(workOrderID);
        }
    }

    private void updateVersion(List<String> workOrderIds) {
        for (String workOrderID : workOrderIds) {
            workOrder_gateway.updateVersion(workOrderID);
        }
    }

    private void linkWorkordersToInvoice(String invoiceId,
            List<String> workOrderIDs) {
        for (String id : workOrderIDs) {
            workOrder_gateway.updateInvoiceId(id, invoiceId);
        }
    }

    private void markWorkOrderAsInvoiced(List<String> ids) {
        for (String id : ids) {
            workOrder_gateway.updateState(id);
        }
    }

}
