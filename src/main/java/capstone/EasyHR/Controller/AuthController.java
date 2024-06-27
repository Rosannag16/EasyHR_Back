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

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/auth") // Mapping principale mantenuto come /auth
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private FerieService ferieService;

    @Autowired
    private PermessoService permessoService;

    @Autowired
    private UserWorkHoursService userWorkHoursService;


    @PostMapping("/register") // Endpoint specificato come /request/register
    public ResponseEntity<Object> register(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) throws BadRequestException {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .reduce("", (s, s2) -> s + " " + s2));
        }

        String userId = userService.saveUtente(userDTO, Ruolo.USER);

        String successMessage = "User with id=" + userId + " correctly saved";
        return ResponseEntity.status(HttpStatus.CREATED).body(successMessage);
    }

    @PostMapping("/login") // Endpoint specificato come /request/login
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Validated UserLoginDTO userLoginDTO, BindingResult bindingResult) throws BadRequestException {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .reduce("", (s, s2) -> s + s2));
        }

        AuthDataDto authDataDto = authService.authenticateUtenteAndCreateToken(userLoginDTO);
        String successMessage = "Utente loggato con successo!";

        LoginResponseDto response = new LoginResponseDto(successMessage, authDataDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users") // Endpoint specificato come /request/users
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUserById(userId);
            return ResponseEntity.ok("Utente eliminato con successo");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utente non trovato con ID: " + userId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore durante l'eliminazione dell'utente con ID: " + userId);
        }
    }
}
