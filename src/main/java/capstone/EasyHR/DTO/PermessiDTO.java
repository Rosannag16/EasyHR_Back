package capstone.EasyHR.DTO;

import lombok.Data;
import java.time.LocalDate;

@Data // Genera automaticamente getter, setter, toString, equals, e hashCode tramite Lombok
public class PermessiDTO {
    // Identificativo unico della richiesta di permesso
    private Long id;

    // Identificativo dell'utente che ha richiesto il permesso
    private Long userId;

    // Data di inizio del permesso
    private LocalDate dataInizio;

    // Data di fine del permesso
    private LocalDate dataFine;

    // Motivo della richiesta di permesso
    private String motivo;

    // Stato della richiesta di permesso (ad esempio, APPROVATO, RIFIUTATO, IN ATTESA)
    private String stato;
}
