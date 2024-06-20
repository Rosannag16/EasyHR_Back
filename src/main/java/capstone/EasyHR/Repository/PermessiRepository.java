package capstone.EasyHR.Repository;

import capstone.EasyHR.Entities.Permessi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermessiRepository extends JpaRepository<Permessi, Long> {
    List<Permessi> findByUserId(Long userId);
}
