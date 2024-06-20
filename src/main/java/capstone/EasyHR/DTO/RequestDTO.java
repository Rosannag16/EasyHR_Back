package capstone.EasyHR.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RequestDTO {
    private Long userId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String motivation;
}
