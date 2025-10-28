package uo.ri.cws.application.ui.cashier.action;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.invoice.InvoicingService;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

/**
 * Class used to invoice one or more workorders in both, FINISHED and
 * NOT_INVOICED states and all of them of the same client.
 */
public class InvoiceWorkorderAction implements Action {

    /* Service used to perform invoice operations. */
    private final InvoicingService service = Factories.service
        .forCreateInvoiceService();

    /**
     * Ask the user for the workorders id to invoice them
     * 
     * @throws BusinessException, the service layer throw it following business
     *                            rules
     */
    @Override
    public void execute() throws BusinessException {
        List<String> workOrdersIds = new ArrayList<String>();
        // Ask the user the work order ids
        do {
            String id = Console.readString("Workorder id");
            workOrdersIds.add(id);
        } while (moreWorkOrders());

        InvoiceDto invoice = service.create(workOrdersIds);
        double amount_without_vat = invoice.amount - invoice.vat;
        displayInvoice(invoice.number, invoice.date, amount_without_vat,
                invoice.vat, invoice.amount);

    }

    /*
     * Ask the user for more workorders
     * 
     * @return true if the user want more workorders and false if not
     */
    private boolean moreWorkOrders() {
        return Console.readString("more work orders? (y/n) ")
            .equalsIgnoreCase("y");
    }

    /* Print the invoice information */
    private void displayInvoice(long numberInvoice, LocalDate dateInvoice,
            double totalInvoice, double vat, double totalConIva) {

        Console.printf("Invoice number: %d\n", numberInvoice);
        Console.printf("\tDate: %1$td/%1$tm/%1$tY\n", dateInvoice);
        Console.printf("\tAmount: %.2f €\n", totalInvoice);
        Console.printf("\tVAT: %.1f %% \n", vat);
        Console.printf("\tTotal (including VAT): %.2f €\n", totalConIva);
    }

}
