package capstone.EasyHR.Entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Permesso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "data_inizio")
    private LocalDate dataInizio;

    @Column(name = "data_fine")
    private LocalDate dataFine;

    private String motivo;

    private String stato; // Esempi: "IN_ATTESA", "APPROVATA", "RESPINTA"


}

