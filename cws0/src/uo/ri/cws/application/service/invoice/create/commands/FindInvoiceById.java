package uo.ri.cws.application.service.invoice.create.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.service.invoice.create.InvoiceAssembler;

public class FindInvoiceById {

    private String id;

    private InvoiceGateway gateway = Factories.persistence.forInvoice();

    public FindInvoiceById(String id) {
        this.id = id;
    }

    public Optional<InvoiceDto> execute() {
        return Optional.of(InvoiceAssembler.toDto(gateway.findById(id).get()));
    }

}
