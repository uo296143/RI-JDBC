package uo.ri.cws.application.persistence.workorder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderRecord;

public interface WorkOrderGateway extends Gateway<WorkOrderRecord> {

	/**
	 * [findWorkordersByMechanicId]: Busca si existen órdenes de trabajo para un mecánico
	 *
	 * @param [mechanicId] String - id del mecánico
	 * @return boolean - True si existen y false en caso contrario
	 *
	 * Ejemplo de uso:
	 * boolean existsWorkOrdersForMechanic = workOrderGateway.existsWorkOrdersForMechanic("sdkjf248348fnfn");
	 */
    public boolean existsWorkordersByMechanicId(
            String mechanicId);

    /**
	 * [findNotInvoicedWorkOrdersByClientNif]: Busca órdenes de trabajo no facturadas para un cliente
	 *
	 * @param [clientNif] String - nif del cliente
	 * @return List<WorkOrderRecord> - lista de Record´s
	 *
	 * Ejemplo de uso:
	 * List<WorkOrderRecord> workOrdersNotInvoiced = workOrderGateway.findNotInvoicedWorkOrdersByClientNif("44442775J");
	 */
    public List<WorkOrderRecord> findNotInvoicedWorkOrdersByClientNif(
            String clientNif);
    
    /**
	 * [findWorkOrdersTotalAmountByMechanicIdInDate]: Busca el coste total de las órdenes de trabajo del mes
	 * anterior para calcular el bonus de productividad en la nómina
	 *
	 * @param [mechanicId] String - id del mecánico
	 * @param [start] LocalDate - primer día del mes
	 * @param [end] LocalDate - últiom día del mes
	 * @return double - cantidad total de las órdenes de trabajo
	 *
	 * Ejemplo de uso:
	 * double totalAmount = workOrderGateway.(findWorkOrdersTotalAmountByMechanicIdInDate("udfudjfkfjk757557h", "01-06-2023", "30-06-2023");
	 */
    public double findWorkOrdersTotalAmountByMechanicIdInDate(String mechanicId,
			LocalDate start, LocalDate end);

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
        public String invoiceId;
        public String mechanicId;
        public String vehicleId;

    }

}
