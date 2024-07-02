package capstone.EasyHR.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data // Genera automaticamente getter, setter, toString, equals, e hashCode tramite Lombok
public class UserDTO {
    // Username dell'utente, non può essere null, vuoto o composto solo da spazi
    @NotBlank(message = "username non può essere null, vuoto, o composta da soli spazi")
    private String username;

    // Email dell'utente, deve essere un indirizzo email valido e non può essere null, vuota o composta solo da spazi
    @Email
    @NotBlank(message = "email non può essere null, vuota, o composta da soli spazi")
    private String email;

    // Password dell'utente, non può essere null, vuota o composta solo da spazi
    @NotBlank(message = "password non può essere null, vuota, o composta da soli spazi")
    private String password;

    // Nome dell'utente, non può essere null, vuoto o composto solo da spazi
    @NotBlank(message = "nome non può essere null, vuota, o composta da soli spazi")
    private String nome;

    // Cognome dell'utente, non può essere null, vuoto o composto solo da spazi
    @NotBlank(message = "cognome non può essere null, vuota, o composta da soli spazi")
    private String cognome;
}
