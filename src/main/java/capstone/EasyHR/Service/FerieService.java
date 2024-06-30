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
    private FerieRepository ferieRepository;

    public List<FerieDTO> getFerieByUserId(Long userId) {
        List<Ferie> ferieList = ferieRepository.findByUserId(userId);
        return ferieList.stream()
                .map(this::convertToFerieDTO)
                .collect(Collectors.toList());
    }

//    public List<FerieDTO> getPendingFerieRequests() {
//        return ferieRepository.findByStato("IN_ATTESA").stream()
//                .map(this::convertToFerieDTO)
//                .collect(Collectors.toList());
//    }

    public void approveFerieRequest(Long ferieId) {
        setStatus(ferieId, "APPROVATA");
    }

    public void rejectFerieRequest(Long ferieId) {
        setStatus(ferieId, "RIFIUTATA");
    }

    public List<FerieDTO> getAllFerie() {
        List<Ferie> ferieList = ferieRepository.findAll();
        return ferieList.stream()
                .map(this::convertToFerieDTO)
                .collect(Collectors.toList());
    }

    public void setStatus(Long ferieId, String status) {
        Optional<Ferie> ferieOptional = ferieRepository.findById(ferieId);
        if (ferieOptional.isPresent()) {
            Ferie ferie = ferieOptional.get();
            ferie.setStato(status);
            ferieRepository.save(ferie);
        } else {
            throw new IllegalArgumentException("Ferie request not found with id: " + ferieId);
        }
    }

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

    public void deleteFerieRequest(Long ferieId) {
        // Implementa la logica per eliminare una richiesta di ferie dal repository
        Optional<Ferie> ferieOptional = ferieRepository.findById(ferieId);
        if (ferieOptional.isPresent()) {
            ferieRepository.deleteById(ferieId);
        } else {
            throw new IllegalArgumentException("Ferie request with id " + ferieId + " not found");
        }
    }

    // Metodo per aggiornare lo stato di un permesso
    public void updateFerieStatus(Long permessoId, String newStatus) {
        setStatus(permessoId, newStatus);
    }
}
