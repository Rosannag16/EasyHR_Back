package capstone.EasyHR.Service;
import capstone.EasyHR.DTO.PermessiDTO;
import capstone.EasyHR.Entities.Permesso;
import capstone.EasyHR.Repository.PermessiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class PermessoService {

    @Autowired
    private PermessiRepository permessoRepository;

    public List<PermessiDTO> getPermessiByUserId(Long userId) {
        List<Permesso> permessoList = permessoRepository.findByUserId(userId);
        List<PermessiDTO> permessiDTOList = permessoList.stream()
                .map(this::convertToPermessiDTO)
                .collect(Collectors.toList());
        return permessiDTOList;
    }

    public List<PermessiDTO> getPendingPermessiRequests() {
        return permessoRepository.findByStato("IN_ATTESA").stream()
                .map(this::convertToPermessiDTO)
                .collect(Collectors.toList());
    }

    public void approvePermessiRequest(Long permessoId) {
        Optional<Permesso> permessoOptional = permessoRepository.findById(permessoId);
        if (permessoOptional.isPresent()) {
            Permesso permesso = permessoOptional.get();
            permesso.setStato("APPROVATO");
            permessoRepository.save(permesso);
        } else {
            throw new IllegalArgumentException("Permesso request not found with id: " + permessoId);
        }
    }

    public void rejectPermessiRequest(Long permessoId) {
        Optional<Permesso> permessoOptional = permessoRepository.findById(permessoId);
        if (permessoOptional.isPresent()) {
            Permesso permesso = permessoOptional.get();
            permesso.setStato("RIFIUTATO");
            permessoRepository.save(permesso);
        } else {
            throw new IllegalArgumentException("Permesso request not found with id: " + permessoId);
        }
    }

    public List<PermessiDTO> getAllPermessi() {
        List<Permesso> permessoList = permessoRepository.findAll();
        return permessoList.stream()
                .map(this::convertToPermessiDTO)
                .collect(Collectors.toList());
    }

    public void setStatus(Long permessoId, String status) {
        Optional<Permesso> permessoOptional = permessoRepository.findById(permessoId);
        if (permessoOptional.isPresent()) {
            Permesso permesso = permessoOptional.get();
            permesso.setStato(status);
            permessoRepository.save(permesso);
        } else {
            throw new IllegalArgumentException("Permesso request not found with id: " + permessoId);
        }
    }

    private PermessiDTO convertToPermessiDTO(Permesso permesso) {
        PermessiDTO permessiDTO = new PermessiDTO();
        permessiDTO.setId(permesso.getId());
        permessiDTO.setUserId(permesso.getUser().getId());
        permessiDTO.setDataInizio(permesso.getDataInizio());
        permessiDTO.setDataFine(permesso.getDataFine());
        permessiDTO.setMotivo(permesso.getMotivo());
        permessiDTO.setStato(permesso.getStato());
        return permessiDTO;
    }

}
