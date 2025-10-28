package uo.ri.cws.application.service.invoice.create;

import uo.ri.cws.application.persistence.invoice.InvoiceGateway.InvoiceRecord;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;

public class InvoiceAssembler {

    public static InvoiceRecord toRecord(InvoiceDto d) {
        InvoiceRecord ir = new InvoiceRecord();
        ir.id = d.id;
        ir.version = d.version;
        ir.amount = d.amount;
        ir.date = d.date;
        ir.number = d.number;
        ir.state = d.state;
        ir.vat = d.vat;
        return ir;
    }

    public static InvoiceDto toDto(InvoiceRecord mr) {
        InvoiceDto idto = new InvoiceDto();
        idto.id = mr.id;
        idto.version = mr.version;
        idto.amount = mr.amount;
        idto.date = mr.date;
        idto.number = mr.number;
        idto.state = mr.state;
        idto.vat = mr.vat;
        return idto;
    }
}
