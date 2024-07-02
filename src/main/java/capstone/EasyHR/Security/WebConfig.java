package capstone.EasyHR.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity(debug = true) // Abilita la configurazione della sicurezza web con debug abilitato
@EnableMethodSecurity // Abilita la sicurezza basata sui metodi
public class WebConfig implements WebMvcConfigurer {

    // Configura le regole CORS per permettere le richieste da http://localhost:4200
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }

    // Configura la catena di filtri di sicurezza per le richieste HTTP
    @Bean
    public SecurityFilterChain securityFilterChainWebConfig(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.formLogin(http -> http.disable()); // Disabilita il login basato su form di Spring Security
        httpSecurity.csrf(http -> http.disable()); // Disabilita la protezione CSRF di Spring Security
        httpSecurity.sessionManagement(http -> http.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // Configura la gestione delle sessioni senza stato
        httpSecurity.cors(Customizer.withDefaults()); // Abilita la configurazione predefinita di CORS

        // Configura le autorizzazioni per le richieste HTTP
        httpSecurity.authorizeHttpRequests(http -> {
            http.requestMatchers("/auth/register").hasAuthority("ADMIN"); // Permette l'accesso a /auth/register solo per gli utenti con autorità ADMIN
            http.requestMatchers("/auth/**").permitAll(); // Permette l'accesso a tutte le richieste sotto /auth/**
            http.anyRequest().denyAll(); // Rifiuta tutte le altre richieste non specificate
        });

        return httpSecurity.build(); // Restituisce la catena di filtri di sicurezza configurata
    }

    // Bean per l'encoder della password
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Utilizza l'algoritmo BCrypt per l'hashing delle password
    }

    // Bean per il service che gestisce gli utenti in memoria
    @Bean
    public UserDetailsService userDetailsService() {
        // Configura un InMemoryUserDetailsManager con un utente amministratore predefinito
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("admin_user@example.com")
                .password(passwordEncoder().encode("admin1"))
                .roles("ADMIN")
                .build());
        return manager;
    }
}
