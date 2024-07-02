package capstone.EasyHR.Repository;

import capstone.EasyHR.Entities.Ferie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FerieRepository extends JpaRepository<Ferie, Long> {
    // Metodo per trovare tutti i permessi di un utente dato il suo ID
    List<Ferie> findByUserId(Long userId);

//    List<Ferie> findByStato(String stato);
}
