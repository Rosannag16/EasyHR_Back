package capstone.EasyHR.Controller;

import capstone.EasyHR.DTO.UserWorkHoursDTO;
import capstone.EasyHR.Service.UserWorkHoursService;
import capstone.EasyHR.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserWorkHoursService userWorkHoursService;

    @PostMapping("/{userId}/workhours")
    public ResponseEntity<UserWorkHoursDTO> addUserWorkHours(
            @PathVariable Long userId,
            @RequestBody UserWorkHoursDTO userWorkHoursDTO) {

        try {
            // Chiamata al servizio per aggiungere le ore di lavoro
            UserWorkHoursDTO createdUserWorkHoursDTO = userWorkHoursService.addUserWorkHours(userId, userWorkHoursDTO);

            // Ritorna la risposta con lo stato 201 Created e il DTO creato
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUserWorkHoursDTO);
        } catch (IllegalArgumentException e) {
            // Se l'utente non è trovato
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            // Gestione generica degli errori interni
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    @GetMapping("/userworkhours")
    public ResponseEntity<List<UserWorkHoursDTO>> getAllUserWorkHours() {
        List<UserWorkHoursDTO> userWorkHoursDTOList = userWorkHoursService.getAllUserWorkHours();
        return ResponseEntity.ok(userWorkHoursDTOList);
    }


}
