package capstone.EasyHR.Repository;

import capstone.EasyHR.Entities.Ferie;
import capstone.EasyHR.Entities.Permesso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermessiRepository extends JpaRepository<Permesso, Long> {
    // Metodo per trovare tutti i permessi di un utente dato il suo ID
    List<Permesso> findByUserId(Long userId);

//    List<Permesso> findByStato(String stato);
}
