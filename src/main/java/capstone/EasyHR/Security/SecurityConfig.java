package capstone.EasyHR.Security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity(debug = true)
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChainSecurityConfig(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.formLogin(e -> e.disable()); // Disabilita il login basato su form
        httpSecurity.csrf(e -> e.disable()); // Disabilita la protezione CSRF
        return httpSecurity.build();
    }


}





