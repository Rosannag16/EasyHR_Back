package capstone.EasyHR.Repository;

import capstone.EasyHR.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Metodo per trovare un utente tramite l'indirizzo email
    Optional<User> findByEmail(String email);
}
