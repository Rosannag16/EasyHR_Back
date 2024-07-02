package capstone.EasyHR.DTO;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data // Genera automaticamente getter, setter, toString, equals, e hashCode tramite Lombok
public class UserWorkHoursDTO {
    // Data in cui sono registrate le ore lavorative
    private LocalDate dataLavoro;

    // Orario di inizio delle ore lavorative registrate
    private LocalTime inizioOraLavoro;

    // Orario di fine delle ore lavorative registrate
    private LocalTime fineOraLavoro;
}
