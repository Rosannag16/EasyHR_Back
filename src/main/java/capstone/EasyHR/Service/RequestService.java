package capstone.EasyHR.Service;

import capstone.EasyHR.DTO.FerieDTO;
import capstone.EasyHR.DTO.PermessiDTO;
import capstone.EasyHR.Entities.Ferie;
import capstone.EasyHR.Entities.Permesso;
import capstone.EasyHR.Entities.User;
import capstone.EasyHR.Entities.UserWorkHours;
import capstone.EasyHR.Repository.FerieRepository;
import capstone.EasyHR.Repository.PermessiRepository;
import capstone.EasyHR.Repository.UserRepository;
import capstone.EasyHR.Repository.UserWorkHoursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestService {

    @Autowired
    private FerieRepository ferieRepository;

    @Autowired
    private PermessiRepository permessiRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserWorkHoursRepository userWorkHoursRepository;

    public Ferie addFerieRequest(FerieDTO ferieDTO) {
        Ferie ferie = new Ferie();
        User user = userRepository.findById(ferieDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        ferie.setUser(user);
        ferie.setDataInizio(ferieDTO.getDataInizio());
        ferie.setDataFine(ferieDTO.getDataFine());
        ferie.setMotivo(ferieDTO.getMotivo());
        return ferieRepository.save(ferie);
    }

    public Permesso addPermessiRequest(PermessiDTO permessiDTO) {
        Permesso permesso = new Permesso();
        User user = userRepository.findById(permessiDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        permesso.setUser(user);
        permesso.setDataInizio(permessiDTO.getDataInizio());
        permesso.setDataFine(permessiDTO.getDataFine());
        permesso.setMotivo(permessiDTO.getMotivo());
        return permessiRepository.save(permesso);
    }

//    public List<Ferie> getFerieRequestsByUserId(Long userId) {
//        return ferieRepository.findByUserId(userId);
//    }
//
//    public List<Permesso> getPermessiRequestsByUserId(Long userId) {
//        return permessiRepository.findByUserId(userId);
//    }
//
//    public UserWorkHours addUserWorkHours(Long userId, UserWorkHours userWorkHours) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//        userWorkHours.setUser(user);
//        return userWorkHoursRepository.save(userWorkHours);
//    }
//
//    public List<UserWorkHours> getUserWorkHoursByUserId(Long userId) {
//        return userWorkHoursRepository.findByUserId(userId);
//    }
}
