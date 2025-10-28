package uo.ri.cws.application.persistence.intervention;

import java.time.LocalDate;
import java.time.LocalDateTime;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.intervention.InterventionGateway.InterventionRecord;

public interface InterventionGateway extends Gateway<InterventionRecord> {

    public boolean findByMechanicId(String mechanic_id);

    public class InterventionRecord {

        public String id;
        public long version;
        public LocalDateTime createAt;
        public LocalDateTime updateAt;
        public String entityState;

        public LocalDate date;
        public int minutes;
        public String mechanic_id;
        public String workorder_id;
    }

}
