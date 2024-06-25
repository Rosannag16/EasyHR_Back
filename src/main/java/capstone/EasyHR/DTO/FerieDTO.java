package capstone.EasyHR.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FerieDTO {
    private Long id;
    private Long userId;
    private LocalDate dataInizio;
    private LocalDate dataFine;
    private String motivo;
    private String stato; // Campo stato aggiunto per rappresentare lo stato della richiesta
}


