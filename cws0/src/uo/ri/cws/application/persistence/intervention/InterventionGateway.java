package uo.ri.cws.application.persistence.intervention;

import java.time.LocalDate;
import java.time.LocalDateTime;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.intervention.InterventionGateway.InterventionRecord;

public interface InterventionGateway extends Gateway<InterventionRecord> {

	/**
	 * [findByMechanicId]: Busca por el id del mecánico si este tiene intervenciones
	 *
	 * @param [mechanicId] String - id del mecánico
	 * @return boolean - True si tiene intervencinoes false en caso contrario
	 *
	 * Ejemplo de uso:
	 * boolean contractType = interventionGateway.findByMechanicId(1jdjd43222j);
	 */
    public boolean findByMechanicId(String mechanicId);

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
