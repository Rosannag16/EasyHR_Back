package capstone.EasyHR.Service;

import capstone.EasyHR.DTO.FerieDTO;
import capstone.EasyHR.Entities.Ferie;
import capstone.EasyHR.Repository.FerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FerieService {

    @Autowired
    private FerieRepository ferieRepository; // Repository per accedere alle entità Ferie nel database

    // Metodo per ottenere tutte le ferie di un utente dato l'ID dell'utente
    public List<FerieDTO> getFerieByUserId(Long userId) {
        List<Ferie> ferieList = ferieRepository.findByUserId(userId);
        return ferieList.stream()
                .map(this::convertToFerieDTO)
                .collect(Collectors.toList());
    }

    // Metodo per approvare una richiesta di ferie dato l'ID della ferie
    public void approveFerieRequest(Long ferieId) {
        setStatus(ferieId, "APPROVATA");
    }

    // Metodo per rifiutare una richiesta di ferie dato l'ID della ferie
    public void rejectFerieRequest(Long ferieId) {
        setStatus(ferieId, "RIFIUTATA");
    }

    // Metodo per ottenere tutte le ferie presenti nel sistema
    public List<FerieDTO> getAllFerie() {
        List<Ferie> ferieList = ferieRepository.findAll();
        return ferieList.stream()
                .map(this::convertToFerieDTO)
                .collect(Collectors.toList());
    }

    // Metodo per impostare lo stato di una ferie dato l'ID e lo stato
    public void setStatus(Long ferieId, String status) {
        Optional<Ferie> ferieOptional = ferieRepository.findById(ferieId);
        if (ferieOptional.isPresent()) {
            Ferie ferie = ferieOptional.get();
            ferie.setStato(status);
            ferieRepository.save(ferie); // Salva la ferie con lo stato aggiornato nel repository
        } else {
            throw new IllegalArgumentException("Richiesta ferie non trovata con ID: " + ferieId);
        }
    }

    // Metodo privato per convertire un'entità Ferie in un DTO FerieDTO
    private FerieDTO convertToFerieDTO(Ferie ferie) {
        FerieDTO ferieDTO = new FerieDTO();
        ferieDTO.setId(ferie.getId());
        ferieDTO.setUserId(ferie.getUser().getId());
        ferieDTO.setDataInizio(ferie.getDataInizio());
        ferieDTO.setDataFine(ferie.getDataFine());
        ferieDTO.setMotivo(ferie.getMotivo());
        ferieDTO.setStato(ferie.getStato());
        return ferieDTO;
    }

    // Metodo per eliminare una richiesta di ferie dato l'ID della ferie
    public void deleteFerieRequest(Long ferieId) {
        Optional<Ferie> ferieOptional = ferieRepository.findById(ferieId);
        if (ferieOptional.isPresent()) {
            ferieRepository.deleteById(ferieId); // Elimina la richiesta di ferie dal repository
        } else {
            throw new IllegalArgumentException("Richiesta ferie con ID " + ferieId + " non trovata");
        }
    }

    // Metodo per aggiornare lo stato di una ferie (metodo wrapper per il metodo setStatus)
    public void updateFerieStatus(Long ferieId, String newStatus) {
        setStatus(ferieId, newStatus);
    }
}
