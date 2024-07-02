package capstone.EasyHR.Controller;

import capstone.EasyHR.DTO.PermessiDTO;
import capstone.EasyHR.Entities.User;
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
public class PermessoController {

    @Autowired
    private RequestService requestService; // Iniezione del servizio richieste

    @Autowired
    private UserService userService; // Iniezione del servizio utente

    @Autowired
    private PermessoService permessoService; // Iniezione del servizio permessi

    @GetMapping("/request/permessi") // Mappa le richieste GET all'endpoint /auth/request/permessi
    public ResponseEntity<List<PermessiDTO>> getPermessiByUserId(@RequestParam Long userId) {
        // Ottiene la lista dei permessi per l'utente specificato
        List<PermessiDTO> permessiDTOList = permessoService.getPermessiByUserId(userId);
        // Restituisce la lista dei permessi con stato 200 (OK)
        return ResponseEntity.ok(permessiDTOList);
    }

    @GetMapping("/request/permessi/all") // Mappa le richieste GET all'endpoint /auth/request/permessi/all
    public ResponseEntity<List<PermessiDTO>> getAllPermessi() {
        // Ottiene la lista di tutti i permessi
        List<PermessiDTO> permessiDTOList = permessoService.getAllPermessi();
        // Restituisce la lista di tutti i permessi con stato 200 (OK)
        return ResponseEntity.ok(permessiDTOList);
    }

    @PostMapping("/request/permessi") // Mappa le richieste POST all'endpoint /auth/request/permessi
    public ResponseEntity<Object> addPermessiRequest(@RequestBody @Valid PermessiDTO permessiDTO) {
        Long userId = permessiDTO.getUserId();

        // Controlla se l'utente esiste
        Optional<User> user = userService.findById(userId);
        if (!user.isPresent()) {
            // Se l'utente non esiste, restituisce uno stato 401 (UNAUTHORIZED) con un messaggio di errore
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized request");
        }

        // Imposta l'ID dell'utente nella richiesta permessi
        permessiDTO.setUserId(user.get().getId());
        // Aggiunge la richiesta permessi
        requestService.addPermessiRequest(permessiDTO);
        // Restituisce uno stato 201 (CREATED) con un messaggio di successo
        return ResponseEntity.status(HttpStatus.CREATED).body(Collections.singletonMap("message", "Permessi request created"));
    }

    @PostMapping("/request/permessi/approve") // Mappa le richieste POST all'endpoint /auth/request/permessi/approve
    public ResponseEntity<Object> approvePermessiRequest(@RequestParam Long permessoId) {
        try {
            // Approva la richiesta permessi con l'ID specificato
            permessoService.approvePermessiRequest(permessoId);
            // Restituisce uno stato 200 (OK) con un messaggio di successo
            return ResponseEntity.ok(Collections.singletonMap("message", "Permessi request approved"));
        } catch (IllegalArgumentException e) {
            // Se la richiesta permessi non viene trovata, restituisce uno stato 404 (NOT FOUND) con un messaggio di errore
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @PostMapping("/request/permessi/reject") // Mappa le richieste POST all'endpoint /auth/request/permessi/reject
    public ResponseEntity<Object> rejectPermessiRequest(@RequestParam Long permessoId) {
        try {
            // Rifiuta la richiesta permessi con l'ID specificato
            permessoService.rejectPermessiRequest(permessoId);
            // Restituisce uno stato 200 (OK) con un messaggio di successo
            return ResponseEntity.ok(Collections.singletonMap("message", "Permessi request rejected"));
        } catch (IllegalArgumentException e) {
            // Se la richiesta permessi non viene trovata, restituisce uno stato 404 (NOT FOUND) con un messaggio di errore
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @PutMapping("/updateStatus/{id}") // Mappa le richieste PUT all'endpoint /auth/updateStatus/{id}
    public ResponseEntity<Map<String, String>> updatePermessiStatus(@PathVariable Long id, @RequestBody Map<String, String> newStatus) {
        try {
            // Ottiene il nuovo stato dalla richiesta
            String status = newStatus.get("stato");
            // Aggiorna lo stato della richiesta permessi con l'ID specificato
            permessoService.updatePermessiStatus(id, status);

            // Crea una risposta di successo
            Map<String, String> response = new HashMap<>();
            response.put("message", "Stato del permesso con id " + id + " aggiornato correttamente a " + status);

            // Restituisce la risposta con stato 200 (OK)
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            // Se c'è un errore durante l'aggiornamento, restituisce uno stato 400 (BAD REQUEST) con un messaggio di errore
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Impossibile aggiornare lo stato del permesso: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @DeleteMapping("/request/permessi/{id}") // Mappa le richieste DELETE all'endpoint /auth/request/permessi/{id}
    public ResponseEntity<Object> deletePermessiRequest(@PathVariable Long id) {
        try {
            // Elimina la richiesta permessi con l'ID specificato
            permessoService.deletePermessiRequest(id);
            // Restituisce uno stato 200 (OK) con un messaggio di successo
            return ResponseEntity.ok(Collections.singletonMap("message", "Permessi request deleted successfully"));
        } catch (IllegalArgumentException e) {
            // Se la richiesta permessi non viene trovata, restituisce uno stato 404 (NOT FOUND) con un messaggio di errore
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", e.getMessage()));
        }
    }
}
