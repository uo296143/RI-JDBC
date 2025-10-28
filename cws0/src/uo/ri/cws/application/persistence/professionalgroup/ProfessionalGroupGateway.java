package uo.ri.cws.application.persistence.professionalgroup;

import java.time.LocalDateTime;
import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupRecord;

public interface ProfessionalGroupGateway
        extends Gateway<ProfessionalGroupRecord> {

    public Optional<ProfessionalGroupRecord> findByName(String name);

    public class ProfessionalGroupRecord {

        public String id;
        public long version;
        public LocalDateTime createAt;
        public LocalDateTime updateAt;
        public String entityState;

        public String name;
        public double productivityRate;
        public double trienniumPayment;
    }

}
