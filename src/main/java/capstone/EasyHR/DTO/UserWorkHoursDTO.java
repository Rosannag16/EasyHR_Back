package capstone.EasyHR.DTO;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class UserWorkHoursDTO {
    private LocalDate dataLavoro;
    private LocalTime inizioOraLavoro;
    private LocalTime fineOraLavoro;

}
