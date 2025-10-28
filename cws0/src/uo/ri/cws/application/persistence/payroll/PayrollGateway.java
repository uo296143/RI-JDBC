package uo.ri.cws.application.persistence.payroll;

import java.time.LocalDate;
import java.time.LocalDateTime;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollRecord;

public interface PayrollGateway extends Gateway<PayrollRecord> {

    public double grossSalaryOfTheLastYear(String id_contrato);

    public boolean findByContractId(String contract_id);

    public int findNumberOfPayrollsByContractId(String contract_id);

    public class PayrollRecord {

        public String id;
        public long version;
        public LocalDateTime createAt;
        public LocalDateTime updateAt;
        public String entityState;

        public double baseSalary;
        public LocalDate date;
        public double extraSalary;
        public double nicDeduction;
        public double productivityEarning;
        public double taxReduction;
        public double trienniumEarning;
        public String contract_id;
    }

}
