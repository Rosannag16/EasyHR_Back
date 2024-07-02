package capstone.EasyHR.Entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalTime;

@Data // Genera automaticamente getter, setter, toString, equals, e hashCode tramite Lombok
@Entity // Classe mappata a livello di database tramite JPA
public class UserWorkHours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generazione automatica dell'ID tramite strategia di auto-incremento
    private Long id;

    @ManyToOne // Relazione molti-a-uno con l'entità User
    @JoinColumn(name = "user_id", nullable = false) // Colonna nel database che rappresenta la relazione con User, non può essere nulla
    private User user;

    @Column(name = "data") // Colonna nel database per la data delle ore lavorative
    private String dataLavoro;

    @Column(name = "inizio_ora_lavoro") // Colonna nel database per l'orario di inizio delle ore lavorative
    private LocalTime inizioOraLavoro;

    @Column(name = "fine_ora_lavoro") // Colonna nel database per l'orario di fine delle ore lavorative
    private LocalTime fineOraLavoro;
}
