package capstone.EasyHR.Entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data // Genera automaticamente getter, setter, toString, equals, e hashCode tramite Lombok
@Entity // Classe mappata a livello di database tramite JPA
public class Ferie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generazione automatica dell'ID tramite strategia di auto-incremento
    private Long id;

    @ManyToOne // Relazione molti-a-uno con l'entità User
    @JoinColumn(name = "user_id", nullable = false) // Colonna nel database che rappresenta la relazione con User, non può essere nulla
    private User user;

    @Column(name = "data_inizio") // Colonna nel database per la data di inizio delle ferie
    private LocalDate dataInizio;

    @Column(name = "data_fine") // Colonna nel database per la data di fine delle ferie
    private LocalDate dataFine;

    private String motivo; // Motivo della richiesta di ferie

    private String stato; // Stato della richiesta di ferie (ad esempio, "IN_ATTESA", "APPROVATA", "RESPINTA")
}
