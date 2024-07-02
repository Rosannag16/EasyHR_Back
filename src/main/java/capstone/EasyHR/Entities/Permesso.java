package capstone.EasyHR.Entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity // Classe mappata a livello di database tramite JPA
@Data // Genera automaticamente getter, setter, toString, equals, e hashCode tramite Lombok
public class Permesso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generazione automatica dell'ID tramite strategia di auto-incremento
    private Long id;

    @ManyToOne // Relazione molti-a-uno con l'entità User
    @JoinColumn(name = "user_id", nullable = false) // Colonna nel database che rappresenta la relazione con User, non può essere nulla
    private User user;

    @Column(name = "data_inizio") // Colonna nel database per la data di inizio del permesso
    private LocalDate dataInizio;

    @Column(name = "data_fine") // Colonna nel database per la data di fine del permesso
    private LocalDate dataFine;

    private String motivo; // Motivo della richiesta di permesso

    private String stato; // Stato della richiesta di permesso (ad esempio, "IN_ATTESA", "APPROVATA", "RESPINTA")
}
