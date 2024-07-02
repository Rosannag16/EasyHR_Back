package capstone.EasyHR.Service;

import capstone.EasyHR.DTO.FerieDTO;
import capstone.EasyHR.DTO.PermessiDTO;
import capstone.EasyHR.Entities.Ferie;
import capstone.EasyHR.Entities.Permesso;
import capstone.EasyHR.Entities.User;
import capstone.EasyHR.Repository.FerieRepository;
import capstone.EasyHR.Repository.PermessiRepository;
import capstone.EasyHR.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestService {

    @Autowired
    private FerieRepository ferieRepository; // Repository per le ferie

    @Autowired
    private PermessiRepository permessiRepository; // Repository per i permessi

    @Autowired
    private UserRepository userRepository; // Repository per gli utenti

    // Metodo per aggiungere una nuova richiesta di ferie
    public Ferie addFerieRequest(FerieDTO ferieDTO) {
        Ferie ferie = new Ferie();

        // Trova l'utente dal repository utilizzando l'ID fornito nel DTO
        User user = userRepository.findById(ferieDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        // Imposta i dettagli della ferie usando i dati dal DTO
        ferie.setUser(user);
        ferie.setDataInizio(ferieDTO.getDataInizio());
        ferie.setDataFine(ferieDTO.getDataFine());
        ferie.setMotivo(ferieDTO.getMotivo());

        // Salva la nuova ferie nel repository e restituisce l'oggetto salvato
        return ferieRepository.save(ferie);
    }

    // Metodo per aggiungere una nuova richiesta di permesso
    public Permesso addPermessiRequest(PermessiDTO permessiDTO) {
        Permesso permesso = new Permesso();

        // Trova l'utente dal repository utilizzando l'ID fornito nel DTO
        User user = userRepository.findById(permessiDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        // Imposta i dettagli del permesso usando i dati dal DTO
        permesso.setUser(user);
        permesso.setDataInizio(permessiDTO.getDataInizio());
        permesso.setDataFine(permessiDTO.getDataFine());
        permesso.setMotivo(permessiDTO.getMotivo());

        // Salva il nuovo permesso nel repository e restituisce l'oggetto salvato
        return permessiRepository.save(permesso);
    }

}
