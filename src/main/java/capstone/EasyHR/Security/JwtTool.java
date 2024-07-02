package capstone.EasyHR.Security;

import capstone.EasyHR.Entities.User;
import capstone.EasyHR.Exceptions.UnauthorizedException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTool {

    @Value("${jwt.secret}")
    private String secret; // Chiave segreta per la firma del token

    @Value("${jwt.duration}")
    private long duration; // Durata di validità del token in millisecondi

    // Metodo per creare un nuovo token JWT per l'utente specificato
    public String createToken(User user) {
        return Jwts.builder()
                .setIssuedAt(new Date(System.currentTimeMillis())) // Data di emissione del token (ora corrente)
                .expiration(new Date(System.currentTimeMillis() + duration)) // Data di scadenza del token (ora corrente + durata)
                .subject(String.valueOf(user.getId())) // Identificatore dell'utente (ID dell'utente)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes())) // Firma del token usando la chiave segreta
                .compact(); // Costruisce il token
    }

    // Metodo per verificare la validità del token JWT
    public void verifyToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(secret.getBytes())) // Verifica il token usando la chiave segreta
                    .build()
                    .parse(token); // Analizza e verifica il token JWT
        } catch (Exception e) {
            throw new UnauthorizedException("Errore nell'autorizzazione, eseguire nuovamente il login!"); // Eccezione se la verifica del token fallisce
        }
    }

    // Metodo per ottenere l'ID dell'utente dal token JWT
    public int getIdFromToken(String token) {
        return Integer.parseInt(
                Jwts.parser()
                        .verifyWith(Keys.hmacShaKeyFor(secret.getBytes())) // Verifica il token usando la chiave segreta
                        .build()
                        .parseSignedClaims(token)
                        .getPayload()
                        .getSubject()); // Ottiene l'identificatore dell'utente dall'oggetto Payload del token
    }
}
