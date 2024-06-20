package capstone.EasyHR.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FerieDTO {
    private Long userId;
    private LocalDate dataInizio;
    private LocalDate dataFine;
    private String motivo;
}
