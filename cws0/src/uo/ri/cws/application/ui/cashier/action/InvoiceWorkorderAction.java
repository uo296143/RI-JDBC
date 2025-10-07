package uo.ri.cws.application.ui.cashier.action;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.invoice.InvoicingService;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class InvoiceWorkorderAction implements Action { 

    InvoicingService service = Factories.service.forCreateInvoiceService();
    
    @Override
	public void execute() throws Exception {
    	List<String> workOrdersIds = new ArrayList<String>();
    	// Ask the user the work order ids
        do {
            String id = Console.readString("Workorder id");
            workOrdersIds.add(id);
        } while (moreWorkOrders());
        
		InvoiceDto invoice = service.create(workOrdersIds);
		displayInvoice(invoice.number, invoice.date, invoice.vat, invoice.amount);
		
	} 

   
    private boolean moreWorkOrders() {
        return Console.readString("more work orders? (y/n) ")
                .equalsIgnoreCase("y");
    }

    private void displayInvoice(long numberInvoice,
            LocalDate dateInvoice,
            //double totalInvoice,
            double vat,
            double totalConIva) {

        Console.printf("Invoice number: %d\n", numberInvoice);
        Console.printf("\tDate: %1$td/%1$tm/%1$tY\n", dateInvoice);
        // Console.printf("\tAmount: %.2f €\n", totalInvoice);
        Console.printf("\tVAT: %.1f %% \n", vat);
        Console.printf("\tTotal (including VAT): %.2f €\n", totalConIva);
    }

	
}
