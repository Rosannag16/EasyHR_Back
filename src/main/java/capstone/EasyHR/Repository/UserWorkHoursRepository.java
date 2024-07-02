package capstone.EasyHR.Repository;

import capstone.EasyHR.Entities.UserWorkHours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface UserWorkHoursRepository extends JpaRepository<UserWorkHours, Long> {
//    List<UserWorkHours> findByUserId(Long userId);
//    Optional<UserWorkHours> findByUser(User user);
//    void deleteByUser(User user);
}
