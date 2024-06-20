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
@EnableWebSecurity(debug = true)
@EnableMethodSecurity
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }

    @Bean
    public SecurityFilterChain securityFilterChainWebConfig(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.formLogin(http -> http.disable()); // Disabilita il login basato su form
        httpSecurity.csrf(http -> http.disable()); // Disabilita la protezione CSRF
        httpSecurity.sessionManagement(http -> http.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // Configura la gestione delle sessioni senza stato
        httpSecurity.cors(Customizer.withDefaults()); // Abilita la configurazione predefinita di CORS

        // Autorizza l'accesso a percorsi specifici
        httpSecurity.authorizeHttpRequests(http -> {
            http.requestMatchers("/auth/register").hasAuthority("ADMIN"); // Permette l'accesso a /auth/register solo per gli utenti con autorità ADMIN
            http.requestMatchers("/auth/**").permitAll(); // Permette l'accesso a /auth/**
            http.anyRequest().denyAll(); // Nega l'accesso agli altri percorsi
        });

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Configura l'encoder di password BCrypt
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // Configura un InMemoryUserDetailsManager con un utente di esempio ADMIN
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("admin_user@example.com")
                .password(passwordEncoder().encode("admin1"))
                .roles("ADMIN")
                .build());
        return manager;
    }


}
