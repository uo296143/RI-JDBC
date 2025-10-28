package uo.ri.cws.application.persistence.invoice;

import java.time.LocalDate;
import java.time.LocalDateTime;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway.InvoiceRecord;

public interface InvoiceGateway extends Gateway<InvoiceRecord> {

    public long findNextNumber();

    public class InvoiceRecord {

        public String id;
        public long version;
        public double amount;
        public LocalDateTime createAt;
        public LocalDateTime updateAt;
        public String entityState;
        public LocalDate date;
        public long number;
        public String state;
        public double vat;

    }

}
