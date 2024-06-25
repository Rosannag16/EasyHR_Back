package capstone.EasyHR.Repository;

import capstone.EasyHR.Entities.User;
import capstone.EasyHR.Entities.UserWorkHours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserWorkHoursRepository extends JpaRepository<UserWorkHours, Long> {
    List<UserWorkHours> findByUserId(Long userId);
    Optional<UserWorkHours> findByUser(User user);
}
