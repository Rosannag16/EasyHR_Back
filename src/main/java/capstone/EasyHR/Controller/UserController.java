package capstone.EasyHR.Controller;

import capstone.EasyHR.DTO.UserWorkHoursDTO;
import capstone.EasyHR.Service.UserWorkHoursService;
import capstone.EasyHR.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Indica che questa classe è un controller REST di Spring
@RequestMapping("/auth") // Specifica il percorso di base per tutti gli endpoint di questo controller
public class UserController {

    @Autowired
    private UserService userService; // Iniezione del servizio utente

    @Autowired
    private UserWorkHoursService userWorkHoursService; // Iniezione del servizio ore lavorative degli utenti

    @PostMapping("/{userId}/workhours") // Mappa le richieste POST all'endpoint /auth/{userId}/workhours
    public ResponseEntity<UserWorkHoursDTO> addUserWorkHours(
            @PathVariable Long userId, // L'ID dell'utente viene passato come parte del percorso dell'URL
            @RequestBody UserWorkHoursDTO userWorkHoursDTO) { // I dati delle ore lavorative dell'utente vengono passati nel corpo della richiesta
        try {
            // Aggiunge le ore lavorative per l'utente specificato
            UserWorkHoursDTO createdUserWorkHoursDTO = userWorkHoursService.addUserWorkHours(userId, userWorkHoursDTO);
            // Restituisce lo stato 201 (CREATED) con i dati delle ore lavorative create
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUserWorkHoursDTO);
        } catch (IllegalArgumentException e) {
            // Se l'utente non viene trovato, restituisce lo stato 404 (NOT FOUND)
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            // Per qualsiasi altro errore, restituisce lo stato 500 (INTERNAL SERVER ERROR)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/userworkhours") // Mappa le richieste GET all'endpoint /auth/userworkhours
    public ResponseEntity<List<UserWorkHoursDTO>> getAllUserWorkHours() {
        // Ottiene la lista di tutte le ore lavorative degli utenti
        List<UserWorkHoursDTO> userWorkHoursDTOList = userWorkHoursService.getAllUserWorkHours();
        // Restituisce la lista con stato 200 (OK)
        return ResponseEntity.ok(userWorkHoursDTOList);
    }
}
