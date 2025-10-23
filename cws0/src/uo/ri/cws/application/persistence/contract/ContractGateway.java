package uo.ri.cws.application.persistence.contract;

import java.time.LocalDate;
import java.time.LocalDateTime;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;

public interface ContractGateway extends Gateway<ContractRecord> {

    public class ContractRecord {
        public String id;
        public long version;
        public LocalDateTime createAt;
        public LocalDateTime updateAt;
        public String entityState;

        public double annualBaseSalary;
        public LocalDate endDate;
        public double settlement;
        public LocalDate startDate;
        public String state;
        public double taxRate;
        public String contractTypeId;
        public String mechanicId;
        public String professionalGroupId;
    }
}
