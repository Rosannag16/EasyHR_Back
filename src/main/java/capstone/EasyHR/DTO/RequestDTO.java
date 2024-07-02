package capstone.EasyHR.DTO;

import lombok.Data;
import java.time.LocalDate;

@Data // Genera automaticamente getter, setter, toString, equals, e hashCode tramite Lombok
public class RequestDTO {
    // Identificativo dell'utente che effettua la richiesta
    private Long userId;

    // Data di inizio della richiesta
    private LocalDate startDate;

    // Data di fine della richiesta
    private LocalDate endDate;

    // Motivazione della richiesta
    private String motivation;
}
