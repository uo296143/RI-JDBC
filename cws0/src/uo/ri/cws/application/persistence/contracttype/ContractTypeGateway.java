package uo.ri.cws.application.persistence.contracttype;

import java.time.LocalDateTime;
import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway.ContractTypeRecord;

public interface ContractTypeGateway extends Gateway<ContractTypeRecord> {

    public Optional<ContractTypeRecord> findByName(String name);

    public class ContractTypeRecord {
        public String id;
        public long version;
        public LocalDateTime createAt;
        public LocalDateTime updateAt;
        public String entityState;

        public double compensationDaysPerYear;
        public String name;
    }
}
