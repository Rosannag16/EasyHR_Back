package capstone.EasyHR.Repository;

import capstone.EasyHR.Entities.Ferie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FerieRepository extends JpaRepository<Ferie, Long> {
    List<Ferie> findByUserId(Long userId);
}
