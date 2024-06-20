package capstone.EasyHR.Service;

import capstone.EasyHR.DTO.FerieDTO;
import capstone.EasyHR.DTO.PermessiDTO;
import capstone.EasyHR.Entities.Ferie;
import capstone.EasyHR.Entities.Permessi;
import capstone.EasyHR.Entities.User;
import capstone.EasyHR.Repository.FerieRepository;
import capstone.EasyHR.Repository.PermessiRepository;
import capstone.EasyHR.Repository.UserRepository;
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

    public Permessi addPermessiRequest(PermessiDTO permessiDTO) {
        Permessi permessi = new Permessi();
        User user = userRepository.findById(permessiDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        permessi.setUser(user);
        permessi.setDataInizio(permessiDTO.getDataInizio());
        permessi.setDataFine(permessiDTO.getDataFine());
        permessi.setMotivo(permessiDTO.getMotivo());
        return permessiRepository.save(permessi);
    }

    public List<Ferie> getFerieRequestsByUserId(Long userId) {
        return ferieRepository.findByUserId(userId);
    }

    public List<Permessi> getPermessiRequestsByUserId(Long userId) {
        return permessiRepository.findByUserId(userId);
    }
}
