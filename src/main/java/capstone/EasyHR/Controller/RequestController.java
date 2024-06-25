package capstone.EasyHR.Controller;

import capstone.EasyHR.DTO.FerieDTO;
import capstone.EasyHR.DTO.PermessiDTO;
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

import java.util.Collections;
import java.util.List;
import java.util.Optional;




@RestController
@RequestMapping("/auth")
public class RequestController {

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

    @GetMapping("/request/permessi")
    public ResponseEntity<List<PermessiDTO>> getPermessiByUserId(@RequestParam Long userId) {
        List<PermessiDTO> permessiDTOList = permessoService.getPermessiByUserId(userId);
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
}
