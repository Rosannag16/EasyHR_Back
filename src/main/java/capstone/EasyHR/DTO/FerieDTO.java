package capstone.EasyHR.DTO;

import lombok.Data;
import java.time.LocalDate;

@Data // Genera automaticamente getter, setter, toString, equals, e hashCode tramite Lombok
public class FerieDTO {
    // Identificativo unico della richiesta di ferie
    private Long id;

    // Identificativo dell'utente che ha richiesto le ferie
    private Long userId;

    // Data di inizio delle ferie
    private LocalDate dataInizio;

    // Data di fine delle ferie
    private LocalDate dataFine;

    // Motivo della richiesta di ferie
    private String motivo;

    // Stato della richiesta di ferie (ad esempio, APPROVATA, RIFIUTATA, IN ATTESA)
    private String stato;
}
