package uo.ri.cws.application.service.payroll.crud;

import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollRecord;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollDto;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryDto;

public class PayrollAssembler {

	public static PayrollDto toDto(PayrollRecord rs){
        PayrollDto result = new PayrollDto();
        result.id = rs.id;
        result.baseSalary = rs.baseSalary;
        result.version = rs.version;
        result.date = rs.date;
        result.extraSalary = rs.extraSalary;
        result.nicDeduction = rs.nicDeduction;
        result.productivityEarning = rs.productivityEarning;
        result.taxDeduction = rs.taxDeduction;
        result.contractId = rs.contractId;
        result.trienniumEarning = rs.trienniumEarning;
        result.grossSalary = rs.baseSalary+rs.extraSalary+rs.productivityEarning+rs.trienniumEarning;
		result.totalDeductions = rs.taxDeduction + rs.nicDeduction;
        result.netSalary = result.grossSalary - result.totalDeductions;
        
        return result;
    }
	
	public static PayrollSummaryDto toSummarizedDto(PayrollRecord rs){
        PayrollSummaryDto result = new PayrollSummaryDto();
        result.id = rs.id;
        result.date = rs.date;
        double grossSalary = rs.baseSalary+rs.extraSalary+rs.productivityEarning+rs.trienniumEarning;
        double totalDeductions = rs.nicDeduction+rs.taxDeduction;
        double netSalary = grossSalary - totalDeductions;
       
        result.netSalary = netSalary;
        
        return result;
    }

}
