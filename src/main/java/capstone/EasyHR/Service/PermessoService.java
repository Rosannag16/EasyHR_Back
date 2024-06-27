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
        return permessoList.stream()
                .map(this::convertToPermessiDTO)
                .collect(Collectors.toList());
    }

    public List<PermessiDTO> getPendingPermessiRequests() {
        return permessoRepository.findByStato("IN_ATTESA").stream()
                .map(this::convertToPermessiDTO)
                .collect(Collectors.toList());
    }

    public void approvePermessiRequest(Long permessoId) {
        setStatus(permessoId, "APPROVATO");
    }

    public void rejectPermessiRequest(Long permessoId) {
        setStatus(permessoId, "RIFIUTATO");
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

    public void deletePermessiRequest(Long permessoId) {
        // Implementa la logica per eliminare una richiesta di permessi dal repository
        Optional<Permesso> permessoOptional = permessoRepository.findById(permessoId);
        if (permessoOptional.isPresent()) {
            permessoRepository.deleteById(permessoId);
        } else {
            throw new IllegalArgumentException("Permessi request with id " + permessoId + " not found");
        }
    }

    // Metodo per aggiornare lo stato di un permesso
    public void updatePermessiStatus(Long permessoId, String newStatus) {
        setStatus(permessoId, newStatus);
    }
}
