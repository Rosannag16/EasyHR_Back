package capstone.EasyHR.Entities;

import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalTime;

@Data
@Entity
public class UserWorkHours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "data")
    private String dataLavoro;

    @Column(name = "inizio_ora_lavoro")
    private LocalTime inizioOraLavoro;

    @Column(name = "fine_ora_lavoro")
    private LocalTime fineOraLavoro;


}
