package capstone.EasyHR.Controller;

import capstone.EasyHR.DTO.*;
import capstone.EasyHR.Enums.Ruolo;
import capstone.EasyHR.Service.*;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Indica che questa classe è un controller REST di Spring
@RequestMapping("/auth") // Specifica il percorso di base per tutti gli endpoint di questo controller
public class AuthController {

    @Autowired
    private AuthService authService; // Iniezione del servizio di autenticazione

    @Autowired
    private UserService userService; // Iniezione del servizio utente

    @Autowired
    private FerieService ferieService; // Iniezione del servizio ferie

    @Autowired
    private PermessoService permessoService; // Iniezione del servizio permesso

    @Autowired
    private UserWorkHoursService userWorkHoursService; // Iniezione del servizio ore lavorative utente

    @PostMapping("/register") // Mappa le richieste POST all'endpoint /auth/register
    public ResponseEntity<Object> register(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) throws BadRequestException {
        // Controlla se ci sono errori di validazione
        if (bindingResult.hasErrors()) {
            // Se ci sono errori, lancia una BadRequestException con i messaggi di errore
            throw new BadRequestException(bindingResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .reduce("", (s, s2) -> s + " " + s2));
        }
        // Salva l'utente e ottieni il suo ID
        String userId = userService.saveUtente(userDTO, Ruolo.USER);
        // Crea un messaggio di successo
        String successMessage = "User with id=" + userId + " correctly saved";
        // Restituisce una risposta con stato 201 (CREATED) e il messaggio di successo
        return ResponseEntity.status(HttpStatus.CREATED).body(successMessage);
    }

    @PostMapping("/login") // Mappa le richieste POST all'endpoint /auth/login
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Validated UserLoginDTO userLoginDTO, BindingResult bindingResult) throws BadRequestException {
        // Controlla se ci sono errori di validazione
        if (bindingResult.hasErrors()) {
            // Se ci sono errori, lancia una BadRequestException con i messaggi di errore
            throw new BadRequestException(bindingResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .reduce("", (s, s2) -> s + s2));
        }
        // Autentica l'utente e crea un token
        AuthDataDto authDataDto = authService.authenticateUtenteAndCreateToken(userLoginDTO);
        // Crea un messaggio di successo
        String successMessage = "Utente loggato con successo!";
        // Crea la risposta con il messaggio di successo e i dati di autenticazione
        LoginResponseDto response = new LoginResponseDto(successMessage, authDataDto);
        // Restituisce una risposta con stato 200 (OK) e i dati di autenticazione
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users") // Mappa le richieste GET all'endpoint /auth/users
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        // Ottiene la lista di tutti gli utenti
        List<UserDTO> users = userService.getAllUsers();
        // Restituisce la lista degli utenti con stato 200 (OK)
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/users/{userId}") // Mappa le richieste DELETE all'endpoint /auth/users/{userId}
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        try {
            // Prova a eliminare l'utente con l'ID specificato
            userService.deleteUserById(userId);
            // Se l'eliminazione ha successo, restituisce un messaggio di successo con stato 200 (OK)
            return ResponseEntity.ok("Utente eliminato con successo");
        } catch (IllegalArgumentException e) {
            // Se l'utente non viene trovato, restituisce un messaggio di errore con stato 404 (NOT FOUND)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utente non trovato con ID: " + userId);
        } catch (Exception e) {
            // Se c'è un altro errore durante l'eliminazione, restituisce un messaggio di errore con stato 500 (INTERNAL SERVER ERROR)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore durante l'eliminazione dell'utente con ID: " + userId);
        }
    }
}
