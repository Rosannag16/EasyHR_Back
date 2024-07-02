package capstone.EasyHR.DTO;

import capstone.EasyHR.Enums.Ruolo; // Importa l'enum Ruolo
import lombok.Data;

@Data // Genera automaticamente getter, setter, toString, equals, e hashCode tramite Lombok
public class AuthDataDto {
    // Identificativo unico dell'utente
    private int id;

    // Token di accesso per l'autenticazione
    private String accessToken;

    // Nome utente
    private String username;

    // Indirizzo email dell'utente
    private String email;

    // Nome dell'utente
    private String nome;

    // Cognome dell'utente
    private String cognome;

    // Ruolo dell'utente (ad esempio, ADMIN, USER, etc.)
    private Ruolo ruolo;
}
