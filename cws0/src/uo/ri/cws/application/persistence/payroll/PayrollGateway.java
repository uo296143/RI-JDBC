package uo.ri.cws.application.persistence.payroll;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollRecord;

public interface PayrollGateway extends Gateway<PayrollRecord> {

	/**
	 * [grossSalaryOfTheLastYear]: Devuelve el salario bruto del último año
	 *
	 * @param [idContrato] String - id del contrato
	 * @param [inicio] LocalDate - fecha de hace un año
	 * @param [fin] LocalDate - fecha de ahora
	 * @return double - Sumatorio del salario bruto del último año
	 *
	 * Ejemplo de uso:
	 * double totalGrossSalary = payrollGateway.grossSalaryOfTheLastYear("jhj3h4h4hhjwef3", "01-03-2022", ;"31-03-2023");
	 */
	public double grossSalaryOfTheLastYear(String idContrato, LocalDate inicio, LocalDate fin);

	/**
	 * [findNumberOfPayrollsByContractId]: Devuelve el número de nóminas que hay para un contrato
	 *
	 * @param [contractId] String - id del contrato
	 * @return int - Número de nóminas para un contrato
	 *
	 * Ejemplo de uso:
	 * int numberOfPayrolls = payrollGateway.finNumberOfPayrollsByContractId("jhj3h4h4hhjwef3");
	 */
	public int findNumberOfPayrollsByContractId(String contractId);
	
	/**
	 * [existsPayrollForContractInDate]: Comprueba si ya se genero la nómina del mes anterior
	 *
	 * @param [contractId] String - id del contrato
	 * @param [finMesAnterior] LocalDate - fin del mes anterior
	 * @return boolean - true si ya se genero
	 *
	 * Ejemplo de uso:
	 * boolean hayYaUnaNomina = payrollGateway.existsPayrollForContractInDate("jhj3h4h4hhjwef3", "02-29-2026");
	 */
	public boolean existsPayrollForContractInDate(String contractId,
			LocalDate finMesAnterior);

	/**
	 * [deletePayrollsOf]: Borra las nóminas del mes anterior
	 *
	 * @param [inicioMesAnterior] LocalDate - inicio del mes anterior
	 * @param [finMesAnterior] LocalDate - fin del mes anterior
	 * @return int - Número de nóminas borradas
	 *
	 * Ejemplo de uso:
	 * int numberOfPayrollsDeleted = payrollGateway.deletePayrolls("01-11-2025", "30-11-2025");
	 */
	public int deletePayrollsOf(LocalDate inicioMesAnterior,
			LocalDate finMesAnterior);

	/**
	 * [deleteLastPayrollsOfMechanicId]: Borra la nómina del último mes del mecánico
	 *
	 * @param [mechanicId] String - id del mecánico
	 * @param [inicioMesAnterior] LocalDate - inicio del mes anterior
	 * @param [finMesAnterior] LocalDate - fin del mes anterior
	 * @return Void - null
	 *
	 * Ejemplo de uso:
	 * payrollGateway.deleteLastPayrollOfMechanicId("ksdfiu7383883hhd", "01-11-2025", "30-11-2025");
	 */
	public Void deleteLastPayrollOfMechanicId(String mechanicId,
			LocalDate inicioMesAnterior, LocalDate finMesAnterior);

	/**
	 * [findPayrollsByMechanicId]: Busca todas las nóminas de un mecánico
	 *
	 * @param [mechanicId] String - id del mecánico
	 * @return List<PayrollRecord> - lista de Record´s
	 *
	 * Ejemplo de uso:
	 * List<PayrollRecord> payrolls = payrollGateway.findPayrollsByMechanicId("ksdfiu7383883hhd");
	 */
	public List<PayrollRecord> findPayrollsByMechanicId(String mechanicId);

	/**
	 * [findPayrollsByProfessionalGroup]: Busca todas las nóminas de un grupo profesional
	 *
	 * @param [name] String - nombre del grupo profesional
	 * @return List<PayrollRecord> - lista de Record´s
	 *
	 * Ejemplo de uso:
	 * List<PayrollRecord> payrolls = payrollGateway.findPayrollsByMechanicId("II");
	 */
	public List<PayrollRecord> findPayrollsByProfessionalGroup(String name);

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
		public double taxDeduction;
		public double trienniumEarning;
		public String contractId;
	}

}
