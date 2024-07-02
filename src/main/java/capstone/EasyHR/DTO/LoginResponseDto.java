package capstone.EasyHR.DTO;

public class LoginResponseDto {
    // Messaggio di risposta, ad esempio "Utente loggato con successo!"
    private String message;

    // Dati di autenticazione dell'utente, contenenti informazioni come il token di accesso e i dettagli dell'utente
    private AuthDataDto authData;

    // Costruttore che inizializza i campi message e authData
    public LoginResponseDto(String message, AuthDataDto authData) {
        this.message = message;
        this.authData = authData;
    }

    // Getter per il campo message
    public String getMessage() {
        return message;
    }

    // Setter per il campo message
    public void setMessage(String message) {
        this.message = message;
    }

    // Getter per il campo authData
    public AuthDataDto getAuthData() {
        return authData;
    }

    // Setter per il campo authData
    public void setAuthData(AuthDataDto authData) {
        this.authData = authData;
    }
}
