package capstone.EasyHR.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class UserDTO {
    @NotBlank(message = "username non può essere null, vuoto, o composta da soli spazi")
    private String username;

    @Email
    @NotBlank(message = "email non può essere null, vuota, o composta da soli spazi")
    private String email;

    @NotBlank(message = "password non può essere null, vuota, o composta da soli spazi")
    private String password;

    @NotBlank(message = "nome non può essere null, vuota, o composta da soli spazi")
    private String nome;

    @NotBlank(message = "cognome non può essere null, vuota, o composta da soli spazi")
    private String cognome;

    private LocalTime inizioOraLavoro;
    private LocalTime fineOraLavoro;
    private LocalDate data;

//    private boolean isAdmin; // Aggiunto campo per indicare se l'utente è un amministratore
}
