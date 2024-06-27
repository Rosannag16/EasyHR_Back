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

@RestController
@RequestMapping("/auth")
public class PermessoController {

    @Autowired
    private RequestService requestService;

    @Autowired
    private UserService userService;

    @Autowired
    private PermessoService permessoService;

    @GetMapping("/request/permessi")
    public ResponseEntity<List<PermessiDTO>> getPermessiByUserId(@RequestParam Long userId) {
        List<PermessiDTO> permessiDTOList = permessoService.getPermessiByUserId(userId);
        return ResponseEntity.ok(permessiDTOList);
    }

    @GetMapping("/request/permessi/all")
    public ResponseEntity<List<PermessiDTO>> getAllPermessi() {
        List<PermessiDTO> permessiDTOList = permessoService.getAllPermessi();
        return ResponseEntity.ok(permessiDTOList);
    }

    @PostMapping("/request/permessi")
    public ResponseEntity<Object> addPermessiRequest(@RequestBody @Valid PermessiDTO permessiDTO) {
        Long userId = permessiDTO.getUserId();

        Optional<User> user = userService.findById(userId);
        if (!user.isPresent()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized request");
        }

        permessiDTO.setUserId(user.get().getId());
        requestService.addPermessiRequest(permessiDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(Collections.singletonMap("message", "Permessi request created"));
    }

    @PostMapping("/request/permessi/approve")
    public ResponseEntity<Object> approvePermessiRequest(@RequestParam Long permessoId) {
        try {
            permessoService.approvePermessiRequest(permessoId);
            return ResponseEntity.ok(Collections.singletonMap("message", "Permessi request approved"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @PostMapping("/request/permessi/reject")
    public ResponseEntity<Object> rejectPermessiRequest(@RequestParam Long permessoId) {
        try {
            permessoService.rejectPermessiRequest(permessoId);
            return ResponseEntity.ok(Collections.singletonMap("message", "Permessi request rejected"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", e.getMessage()));
        }
    }
    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<Map<String, String>> updatePermessiStatus(@PathVariable Long id, @RequestBody Map<String, String> newStatus) {
        try {
            String status = newStatus.get("stato");
            permessoService.updatePermessiStatus(id, status);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Stato del permesso con id " + id + " aggiornato correttamente a " + status);

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Impossibile aggiornare lo stato del permesso: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }



    @DeleteMapping("/request/permessi/{id}")
    public ResponseEntity<Object> deletePermessiRequest(@PathVariable Long id) {
        try {
            permessoService.deletePermessiRequest(id);
            return ResponseEntity.ok(Collections.singletonMap("message", "Permessi request deleted successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", e.getMessage()));
        }
    }
}
