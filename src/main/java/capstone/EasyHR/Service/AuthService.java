package capstone.EasyHR.Service;

import capstone.EasyHR.DTO.AuthDataDto;
import capstone.EasyHR.DTO.UserLoginDTO;
import capstone.EasyHR.Entities.User;
import capstone.EasyHR.Exceptions.NotFoundException;
import capstone.EasyHR.Exceptions.UnauthorizedException;
import capstone.EasyHR.Security.JwtTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserService userService; // Inietta il servizio UserService per interagire con i dati degli utenti

    @Autowired
    private JwtTool jwtTool; // Inietta JwtTool per la creazione e la verifica del token JWT

    @Autowired
    private PasswordEncoder passwordEncoder; // Inietta PasswordEncoder per l'hashing e la verifica delle password

    // Metodo per autenticare l'utente e creare il token JWT
    public AuthDataDto authenticateUtenteAndCreateToken(UserLoginDTO userLoginDTO) {
        // Recupera le informazioni dell'utente tramite l'email
        Optional<User> userOptional = userService.getUtenteByEmail(userLoginDTO.getEmail());

        // Verifica se l'utente esiste
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Verifica se la password fornita corrisponde alla password hashata memorizzata nel database
            if (passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword())) {
                // Crea l'oggetto AuthDataDto per contenere i dati di autenticazione
                AuthDataDto authDataDto = new AuthDataDto();
                authDataDto.setAccessToken(jwtTool.createToken(user)); // Crea il token JWT usando JwtTool
                authDataDto.setRuolo(user.getRuolo()); // Imposta il ruolo dell'utente
                authDataDto.setNome(user.getNome()); // Imposta il nome dell'utente
                authDataDto.setCognome(user.getCognome()); // Imposta il cognome dell'utente
                authDataDto.setEmail(user.getEmail()); // Imposta l'email dell'utente
                authDataDto.setUsername(user.getUsername()); // Imposta lo username dell'utente
                authDataDto.setId(Math.toIntExact(user.getId())); // Imposta l'ID dell'utente (convertito in intero)

                return authDataDto; // Restituisce l'oggetto AuthDataDto contenente i dati di autenticazione
            } else {
                // Lancia UnauthorizedException se le password non corrispondono
                throw new UnauthorizedException("Errore nel login, riloggarsi");
            }
        } else {
            // Lancia NotFoundException se l'utente con l'email specificata non viene trovato
            throw new NotFoundException("Utente con email " + userLoginDTO.getEmail() + " non trovato");
        }
    }
}
