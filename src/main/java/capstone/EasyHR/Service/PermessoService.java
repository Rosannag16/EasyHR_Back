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
    private PermessiRepository permessoRepository; // Repository per accedere alle entità Permesso nel database

    // Metodo per ottenere tutti i permessi di un utente dato l'ID dell'utente
    public List<PermessiDTO> getPermessiByUserId(Long userId) {
        List<Permesso> permessoList = permessoRepository.findByUserId(userId);
        return permessoList.stream()
                .map(this::convertToPermessiDTO)
                .collect(Collectors.toList());
    }

    // Metodo per approvare una richiesta di permesso dato l'ID del permesso
    public void approvePermessiRequest(Long permessoId) {
        setStatus(permessoId, "APPROVATO");
    }

    // Metodo per rifiutare una richiesta di permesso dato l'ID del permesso
    public void rejectPermessiRequest(Long permessoId) {
        setStatus(permessoId, "RIFIUTATO");
    }

    // Metodo per ottenere tutti i permessi presenti nel sistema
    public List<PermessiDTO> getAllPermessi() {
        List<Permesso> permessoList = permessoRepository.findAll();
        return permessoList.stream()
                .map(this::convertToPermessiDTO)
                .collect(Collectors.toList());
    }

    // Metodo per impostare lo stato di un permesso dato l'ID e lo stato specificato
    public void setStatus(Long permessoId, String status) {
        Optional<Permesso> permessoOptional = permessoRepository.findById(permessoId);
        if (permessoOptional.isPresent()) {
            Permesso permesso = permessoOptional.get();
            permesso.setStato(status);
            permessoRepository.save(permesso); // Salva il permesso con lo stato aggiornato nel repository
        } else {
            throw new IllegalArgumentException("Richiesta permesso non trovata con ID: " + permessoId);
        }
    }

    // Metodo privato per convertire un'entità Permesso in un DTO PermessiDTO
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

    // Metodo per eliminare una richiesta di permesso dato l'ID del permesso
    public void deletePermessiRequest(Long permessoId) {
        Optional<Permesso> permessoOptional = permessoRepository.findById(permessoId);
        if (permessoOptional.isPresent()) {
            permessoRepository.deleteById(permessoId); // Elimina la richiesta di permesso dal repository
        } else {
            throw new IllegalArgumentException("Richiesta permesso con ID " + permessoId + " non trovata");
        }
    }

    // Metodo per aggiornare lo stato di un permesso (metodo wrapper per il metodo setStatus)
    public void updatePermessiStatus(Long permessoId, String newStatus) {
        setStatus(permessoId, newStatus);
    }
}
