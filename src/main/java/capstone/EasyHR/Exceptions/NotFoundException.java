package capstone.EasyHR.Exceptions;

// Eccezione personalizzata per gestire casi in cui una risorsa non viene trovata
public class NotFoundException extends RuntimeException {

    // Costruttore che accetta un messaggio di errore
    public NotFoundException(String message) {
        super(message); // Richiama il costruttore della classe madre (RuntimeException) con il messaggio specificato
    }
}
