package uo.ri.cws.application.persistence.mechanic;

import java.time.LocalDateTime;
import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;

public interface MechanicGateway extends Gateway<MechanicRecord>{
	
	/**
	 * [findByNif]: Busca un mecánico por nif
	 *
	 * @param [nif] String - nif del mecánico
	 * @return Optional<MechanicRecord> - MechanicRecord si existe
	 *
	 * Ejemplo de uso:
	 * Optional<MechanicRecord> mechanic = mechanicGateway.findByNif(nif);
	 */
	public Optional<MechanicRecord> findByNif(String nif);
	
	public class MechanicRecord {
		public String id;
		public long version;
		public LocalDateTime createAt;
		public LocalDateTime updateAt;
		public String entityState;
		
		public String nif;
		public String name;
		public String surname;

	}

}
