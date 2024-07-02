package capstone.EasyHR.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data // Genera automaticamente getter, setter, toString, equals, e hashCode tramite Lombok
public class UserLoginDTO {
    // Email dell'utente per il login, deve essere un indirizzo email valido e non può essere null, vuota o composta solo da spazi
    @Email
    @NotBlank(message = "l'email non può essere vuota o mancante o composta da soli spazi")
    private String email;

    // Password dell'utente per il login, non può essere null, vuota o composta solo da spazi
    @NotBlank(message = "La password non può essere vuota o mancante o composta da soli spazi")
    private String password;
}
