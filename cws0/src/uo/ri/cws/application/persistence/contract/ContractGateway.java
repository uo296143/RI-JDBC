package uo.ri.cws.application.persistence.contract;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;

public interface ContractGateway extends Gateway<ContractRecord> {

	/**
	 * [findInForceContractByMechanicId]: Busca el contrato actual del mecánico si lo tiene
	 *
	 * @param [mechanicId] String - El id del mecánico
	 * @return Optional<ContractRecord> - Devuelve el Record de Contract si existe este
	 *
	 * Ejemplo de uso:
	 * Optional<ContractRecord> contract = contractGateway.findInForceContractByMechanicId(1h2b12h12h12b);
	 */
    public Optional<ContractRecord> findInForceContractByMechanicId(String mecahnicId);

    /**
	 * [findContractsByMechanicId]: Busca todos los contratos del mecánico
	 *
	 * @param [mechanicId] String - El id del mecánico
	 * @return List<ContractRecord> - Lista de Record´s de Contract
	 *
	 * Ejemplo de uso:
	 * List<ContractRecord> contractList = contractGateway.findContractsByMechanicId(1h2b12h12h12b);
	 */
    public List<ContractRecord> findContractsByMechanicId(String mecahnicId);

    /**
	 * [findContractBetween]: Busca todos los contratos para un mes
	 *
	 * @param [inicioMesAnterior] String - Primer día del mes
	 * @param [finMesAnterior] String - Último día del mes
	 * @return List<ContractRecord> - Lista de Record´s de Contract
	 *
	 * Ejemplo de uso:
	 * List<ContractRecord> contractList = contractGateway.findContractBetween("01-04-2025","30-04-2025");
	 */
    public List<ContractRecord> findContractBetween(LocalDate inicioMesAnterior,
			LocalDate finMesAnterior);

    /**
	 * [existsContractsForProfessionalGroup]: Busca si ese grupo profesional tiene algun contrato
	 *
	 * @param [name] String - El nombre del grupo profesional
	 * @return boolean - True si existen y false en caso contrario
	 *
	 * Ejemplo de uso:
	 * boolean existeContrato = contractGateway.existsContractsForProfessionalGroup(I);
	 */
	public boolean existsContractsForProfessionalGroup(String name);

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
