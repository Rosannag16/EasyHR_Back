package capstone.EasyHR.Controller;

import capstone.EasyHR.DTO.FerieDTO;
import capstone.EasyHR.Entities.User;
import capstone.EasyHR.Service.FerieService;
import capstone.EasyHR.Service.PermessoService;
import capstone.EasyHR.Service.RequestService;
import capstone.EasyHR.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController // Indica che questa classe è un controller REST di Spring
@RequestMapping("/auth") // Specifica il percorso di base per tutti gli endpoint di questo controller
public class FerieController {

    @Autowired
    private RequestService requestService; // Iniezione del servizio richieste

    @Autowired
    private UserService userService; // Iniezione del servizio utente

    @Autowired
    private FerieService ferieService; // Iniezione del servizio ferie

    @Autowired
    private PermessoService permessoService; // Iniezione del servizio permessi

    @GetMapping("/request/ferie") // Mappa le richieste GET all'endpoint /auth/request/ferie
    public ResponseEntity<List<FerieDTO>> getFerieByUserId(@RequestParam Long userId) {
        // Ottiene la lista delle ferie per l'utente specificato
        List<FerieDTO> ferieDTOList = ferieService.getFerieByUserId(userId);
        // Restituisce la lista delle ferie con stato 200 (OK)
        return ResponseEntity.ok(ferieDTOList);
    }

    @GetMapping("/request/ferie/all") // Mappa le richieste GET all'endpoint /auth/request/ferie/all
    public ResponseEntity<List<FerieDTO>> getAllFerie() {
        // Ottiene la lista di tutte le ferie
        List<FerieDTO> ferieDTOList = ferieService.getAllFerie();
        // Restituisce la lista di tutte le ferie con stato 200 (OK)
        return ResponseEntity.ok(ferieDTOList);
    }

    @PostMapping("/request/ferie") // Mappa le richieste POST all'endpoint /auth/request/ferie
    public ResponseEntity<Object> addFerieRequest(@RequestBody @Valid FerieDTO ferieDTO) {
        Long userId = ferieDTO.getUserId();

        // Controlla se l'utente esiste
        Optional<User> user = userService.findById(userId);
        if (!user.isPresent()) {
            // Se l'utente non esiste, restituisce uno stato 401 (UNAUTHORIZED) con un messaggio di errore
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized request");
        }

        // Imposta l'ID dell'utente nella richiesta ferie
        ferieDTO.setUserId(user.get().getId());
        // Aggiunge la richiesta ferie
        requestService.addFerieRequest(ferieDTO);
        // Restituisce uno stato 201 (CREATED) con un messaggio di successo
        return ResponseEntity.status(HttpStatus.CREATED).body(Collections.singletonMap("message", "Ferie request created"));
    }

    @PostMapping("/request/ferie/approve") // Mappa le richieste POST all'endpoint /auth/request/ferie/approve
    public ResponseEntity<Object> approveFerieRequest(@RequestParam Long ferieId) {
        try {
            // Approva la richiesta ferie con l'ID specificato
            ferieService.approveFerieRequest(ferieId);
            // Restituisce uno stato 200 (OK) con un messaggio di successo
            return ResponseEntity.ok(Collections.singletonMap("message", "Ferie request approved"));
        } catch (IllegalArgumentException e) {
            // Se la richiesta ferie non viene trovata, restituisce uno stato 404 (NOT FOUND) con un messaggio di errore
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @PostMapping("/request/ferie/reject") // Mappa le richieste POST all'endpoint /auth/request/ferie/reject
    public ResponseEntity<Object> rejectFerieRequest(@RequestParam Long ferieId) {
        try {
            // Rifiuta la richiesta ferie con l'ID specificato
            ferieService.rejectFerieRequest(ferieId);
            // Restituisce uno stato 200 (OK) con un messaggio di successo
            return ResponseEntity.ok(Collections.singletonMap("message", "Ferie request rejected"));
        } catch (IllegalArgumentException e) {
            // Se la richiesta ferie non viene trovata, restituisce uno stato 404 (NOT FOUND) con un messaggio di errore
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @PutMapping("/request/ferie/updateStatus/{id}") // Mappa le richieste PUT all'endpoint /auth/request/ferie/updateStatus/{id}
    public ResponseEntity<Map<String, String>> updateFerieStatus(@PathVariable Long id, @RequestBody Map<String, String> newStatus) {
        try {
            // Ottiene il nuovo stato dalla richiesta
            String status = newStatus.get("stato");
            // Aggiorna lo stato della richiesta ferie con l'ID specificato
            ferieService.updateFerieStatus(id, status);
            // Crea una risposta di successo
            Map<String, String> response = new HashMap<>();
            response.put("message", "Stato della ferie con id " + id + " aggiornato correttamente a " + status);
            // Restituisce la risposta con stato 200 (OK)
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            // Se c'è un errore durante l'aggiornamento, restituisce uno stato 400 (BAD REQUEST) con un messaggio di errore
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Impossibile aggiornare lo stato delle ferie: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @DeleteMapping("/request/ferie/{id}") // Mappa le richieste DELETE all'endpoint /auth/request/ferie/{id}
    public ResponseEntity<Object> deleteFerieRequest(@PathVariable Long id) {
        try {
            // Elimina la richiesta ferie con l'ID specificato
            ferieService.deleteFerieRequest(id);
            // Restituisce uno stato 200 (OK) con un messaggio di successo
            return ResponseEntity.ok(Collections.singletonMap("message", "Ferie request deleted successfully"));
        } catch (IllegalArgumentException e) {
            // Se la richiesta ferie non viene trovata, restituisce uno stato 404 (NOT FOUND) con un messaggio di errore
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", e.getMessage()));
        }
    }
}
