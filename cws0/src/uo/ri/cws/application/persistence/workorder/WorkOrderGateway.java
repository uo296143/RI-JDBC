package uo.ri.cws.application.persistence.workorder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderRecord;

public interface WorkOrderGateway extends Gateway<WorkOrderRecord> {

    public String findStatusById(String id);

    public void updateState(String id);

    public void updateInvoiceId(String id, String invoiceId);

    public void updateVersion(String id);

    public void updateTimeStamp(String id);

    public double findAmountById(String id);

    public Optional<MechanicRecord> findWorkordersByMechanicId(
            String mechanic_id);

    public List<WorkOrderRecord> findNotInvoicedWorkOrdersByClientNif(
            String client_nif);

    public class WorkOrderRecord {

        public String id;
        public long version;
        public double amount;
        public LocalDateTime createAt;
        public LocalDateTime updateAt;
        public String entityState;
        public LocalDate date;
        public long number;
        public String state;
        public String description;
        public String invoice_id;
        public String mechanic_id;
        public String vehicle_id;

    }

}
