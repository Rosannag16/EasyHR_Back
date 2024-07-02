package capstone.EasyHR.Security;

import capstone.EasyHR.Entities.User;
import capstone.EasyHR.Exceptions.NotFoundException;
import capstone.EasyHR.Exceptions.UnauthorizedException;
import capstone.EasyHR.Service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTool jwtTool;

    @Autowired
    private UserService userService;

    // Questo metodo viene chiamato per ogni richiesta HTTP
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Controlla se la richiesta non deve essere filtrata
        if (!shouldNotFilter(request)) {
            // Ottiene l'header Authorization
            String authHeader = request.getHeader("Authorization");

            // Controlla se l'header è presente e inizia con "Bearer "
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new UnauthorizedException("Errore nell'autorizzazione, token mancante!");
            }

            // Estrae il token dall'header
            String token = authHeader.substring(7);

            // Verifica la validità del token
            jwtTool.verifyToken(token);

            // Ottiene l'ID utente dal token
            int userId = jwtTool.getIdFromToken(token);

            // Ottiene l'utente dal service utilizzando l'ID
            Optional<User> userOptional = userService.getUtenteById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();

                // Crea un oggetto Authentication per l'utente
                Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

                // Imposta l'oggetto Authentication nel contesto di sicurezza
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                throw new NotFoundException("Utente con id=" + userId + " non trovato");
            }
        }

        // Prosegue con la catena dei filtri
        filterChain.doFilter(request, response);
    }

    // Metodo per determinare se una richiesta non deve essere filtrata
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // Utilizza AntPathMatcher per verificare se il percorso della servlet della richiesta corrisponde a "/auth/**"
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }
}
