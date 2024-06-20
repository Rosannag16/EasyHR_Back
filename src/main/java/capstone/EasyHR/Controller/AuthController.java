package capstone.EasyHR.Controller;

import capstone.EasyHR.DTO.AuthDataDto;
import capstone.EasyHR.DTO.LoginResponseDto;
import capstone.EasyHR.DTO.UserDTO;
import capstone.EasyHR.DTO.UserLoginDTO;
import capstone.EasyHR.Enums.Ruolo;
import capstone.EasyHR.Service.AuthService;
import capstone.EasyHR.Service.UserService;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) throws BadRequestException {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .reduce("", (s, s2) -> s + " " + s2));
        }

        // Esegui la logica di registrazione
        String userId = userService.saveUtente(userDTO, Ruolo.USER);

        // Costruisci una risposta JSON con un messaggio di conferma
        String successMessage = "User with id=" + userId + " correctly saved";

        // Restituisci una ResponseEntity con il messaggio e lo stato OK (200)
        return ResponseEntity.status(HttpStatus.CREATED).body(successMessage);
    }




    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Validated UserLoginDTO userLoginDTO, BindingResult bindingResult) throws BadRequestException {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .reduce("", (s, s2) -> s + s2));
        }

        AuthDataDto authDataDto = authService.authenticateUtenteAndCreateToken(userLoginDTO);
        String successMessage = "Utente loggato con successo!"; // Messaggio di successo

        // Costruisci la risposta da restituire
        LoginResponseDto response = new LoginResponseDto(successMessage, authDataDto);

        // Restituisci la risposta come ResponseEntity con stato OK
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUserById(userId);
            return ResponseEntity.ok("Utente eliminato con successo");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore durante l'eliminazione dell'utente");
        }
    }


    // Altri metodi già presenti nel tuo controller per register e login...
}