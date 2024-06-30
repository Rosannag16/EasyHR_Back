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

@RestController
@RequestMapping("/auth")
public class FerieController {

    @Autowired
    private RequestService requestService;

    @Autowired
    private UserService userService;

    @Autowired
    private FerieService ferieService;

    @Autowired
    private PermessoService permessoService;

    @GetMapping("/request/ferie")
    public ResponseEntity<List<FerieDTO>> getFerieByUserId(@RequestParam Long userId) {
        List<FerieDTO> ferieDTOList = ferieService.getFerieByUserId(userId);
        return ResponseEntity.ok(ferieDTOList);
    }

    @GetMapping("/request/ferie/all")
    public ResponseEntity<List<FerieDTO>> getAllFerie() {
        List<FerieDTO> ferieDTOList = ferieService.getAllFerie();
        return ResponseEntity.ok(ferieDTOList);
    }

    @PostMapping("/request/ferie")
    public ResponseEntity<Object> addFerieRequest(@RequestBody @Valid FerieDTO ferieDTO) {
        Long userId = ferieDTO.getUserId();

        Optional<User> user = userService.findById(userId);
        if (!user.isPresent()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized request");
        }

        ferieDTO.setUserId(user.get().getId());
        requestService.addFerieRequest(ferieDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(Collections.singletonMap("message", "Ferie request created"));
    }

    @PostMapping("/request/ferie/approve")
    public ResponseEntity<Object> approveFerieRequest(@RequestParam Long ferieId) {
        try {
            ferieService.approveFerieRequest(ferieId);
            return ResponseEntity.ok(Collections.singletonMap("message", "Ferie request approved"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @PostMapping("/request/ferie/reject")
    public ResponseEntity<Object> rejectFerieRequest(@RequestParam Long ferieId) {
        try {
            ferieService.rejectFerieRequest(ferieId);
            return ResponseEntity.ok(Collections.singletonMap("message", "Ferie request rejected"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", e.getMessage()));
        }
    }



    @PutMapping("/request/ferie/updateStatus/{id}")
    public ResponseEntity<Map<String, String>> updateFerieStatus(@PathVariable Long id, @RequestBody Map<String, String> newStatus) {
        try {
            String status = newStatus.get("stato"); // Estrai lo stato dalle informazioni ricevute
            // Chiamata al servizio per aggiornare lo stato delle ferie
            ferieService.updateFerieStatus(id, status);

            // Costruisci la risposta JSON
            Map<String, String> response = new HashMap<>();
            response.put("message", "Stato della ferie con id " + id + " aggiornato correttamente a " + status);

            // Ritorna una ResponseEntity con status OK e il corpo della risposta
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            // Se si verifica un'eccezione, costruisci una risposta di errore
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Impossibile aggiornare lo stato delle ferie: " + e.getMessage());

            // Ritorna una ResponseEntity con status BAD_REQUEST e il corpo dell'errore
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

        @DeleteMapping("/request/ferie/{id}")
        public ResponseEntity<Object> deleteFerieRequest(@PathVariable Long id) {
            try {
                ferieService.deleteFerieRequest(id);
                return ResponseEntity.ok(Collections.singletonMap("message", "Ferie request deleted successfully"));
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", e.getMessage()));
            }
        }



}
