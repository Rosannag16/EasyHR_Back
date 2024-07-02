package capstone.EasyHR.Exceptions;

// Eccezione personalizzata per gestire casi di accesso non autorizzato
public class UnauthorizedException extends RuntimeException {

    // Costruttore che accetta un messaggio di errore
    public UnauthorizedException(String message) {
        super(message); // Richiama il costruttore della classe madre (RuntimeException) con il messaggio specificato
    }
}
