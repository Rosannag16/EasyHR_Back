package capstone.EasyHR.Entities;

import capstone.EasyHR.Enums.Ruolo;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data // Genera automaticamente getter, setter, toString, equals, e hashCode tramite Lombok
@Entity // Classe mappata a livello di database tramite JPA
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generazione automatica dell'ID tramite strategia di auto-incremento
    private Long id;

    private String username; // Nome utente per l'autenticazione

    private String email; // Indirizzo email dell'utente

    private String password; // Password dell'utente (non criptata, per esempio)

    private String nome; // Nome dell'utente

    private String cognome; // Cognome dell'utente

    @Enumerated(EnumType.STRING) // Tipo enumerato Ruolo rappresentato come stringa nel database
    private Ruolo ruolo; // Ruolo dell'utente (ENUM: ADMIN, USER, ecc.)

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL) // Relazione uno-a-molti con UserWorkHours
    private List<UserWorkHours> workHoursList; // Lista delle ore lavorative dell'utente

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL) // Relazione uno-a-molti con Ferie
    private List<Ferie> ferieList; // Lista delle ferie richieste dall'utente

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL) // Relazione uno-a-molti con Permesso
    private List<Permesso> permessoList; // Lista dei permessi richiesti dall'utente

    // Metodo dell'interfaccia UserDetails per ottenere le autorità dell'utente (ruolo)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(ruolo.name()));
    }

    // Metodi dell'interfaccia UserDetails per gestire lo stato dell'account (sempre true in questo esempio)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // Metodo getter per ottenere il nome utente
    @Override
    public String getUsername() {
        return username;
    }
}
