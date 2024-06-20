package capstone.EasyHR.DTO;


import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class UpdateUserWorkHoursDTO {
    private LocalDate data;
    private LocalTime inizioOraLavoro;
    private LocalTime fineOraLavoro;
}

