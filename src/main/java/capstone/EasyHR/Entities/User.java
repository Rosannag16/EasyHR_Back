package capstone.EasyHR.Entities;

import capstone.EasyHR.Enums.Ruolo;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;

    private String password;

    private String nome;

    private String cognome;

    @Enumerated(EnumType.STRING)
    private Ruolo ruolo;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Ferie> ferieList;

    // Relazione con le richieste di permessi
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Permesso> permessoList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(ruolo.name()));
    }

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

    @Override
    public String getUsername() {
        return username;
    }
}
