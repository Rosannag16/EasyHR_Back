package capstone.EasyHR.Service;

import capstone.EasyHR.DTO.UserWorkHoursDTO;
import capstone.EasyHR.Entities.User;
import capstone.EasyHR.Entities.UserWorkHours;
import capstone.EasyHR.Repository.UserRepository;
import capstone.EasyHR.Repository.UserWorkHoursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserWorkHoursService {

    @Autowired
    private UserWorkHoursRepository userWorkHoursRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public UserWorkHoursDTO addUserWorkHours(Long userId, UserWorkHoursDTO userWorkHoursDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        UserWorkHours userWorkHours = new UserWorkHours();
        userWorkHours.setUser(user);
        userWorkHours.setDataLavoro(String.valueOf(userWorkHoursDTO.getDataLavoro()));
        userWorkHours.setInizioOraLavoro(userWorkHoursDTO.getInizioOraLavoro());
        userWorkHours.setFineOraLavoro(userWorkHoursDTO.getFineOraLavoro());

        userWorkHoursRepository.save(userWorkHours);
        return userWorkHoursDTO;
    }

//    public UserWorkHoursDTO updateUserWorkHours(Long id, UserWorkHoursDTO userWorkHoursDTO) {
//        UserWorkHours userWorkHours = userWorkHoursRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("User work hours not found"));
//
//        userWorkHours.setDataLavoro(String.valueOf(userWorkHoursDTO.getDataLavoro()));
//        userWorkHours.setInizioOraLavoro(userWorkHoursDTO.getInizioOraLavoro());
//        userWorkHours.setFineOraLavoro(userWorkHoursDTO.getInizioOraLavoro());
//
//        userWorkHours = userWorkHoursRepository.save(userWorkHours);
//
//        return buildUserWorkHoursDTO(userWorkHours);
//    }

    public List<UserWorkHoursDTO> getAllUserWorkHours() {
        List<UserWorkHours> userWorkHoursList = userWorkHoursRepository.findAll();
        return userWorkHoursList.stream()
                .map(this::buildUserWorkHoursDTO)
                .collect(Collectors.toList());
    }

    private UserWorkHoursDTO buildUserWorkHoursDTO(UserWorkHours userWorkHours) {
        UserWorkHoursDTO dto = new UserWorkHoursDTO();

        dto.setDataLavoro(LocalDate.parse(userWorkHours.getDataLavoro()));
        dto.setInizioOraLavoro(LocalTime.parse(userWorkHours.getInizioOraLavoro().format(DateTimeFormatter.ofPattern("HH:mm:ss"))));
        dto.setFineOraLavoro(LocalTime.parse(userWorkHours.getFineOraLavoro().format(DateTimeFormatter.ofPattern("HH:mm:ss"))));
        return dto;
    }
}
