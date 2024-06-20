package capstone.EasyHR.Controller;

import capstone.EasyHR.DTO.FerieDTO;
import capstone.EasyHR.DTO.PermessiDTO;
import capstone.EasyHR.Entities.Ferie;
import capstone.EasyHR.Entities.Permessi;
import capstone.EasyHR.Entities.User;
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

    @PostMapping("/ferie")
    public ResponseEntity<Object> addFerieRequest(@RequestBody @Valid FerieDTO ferieDTO) {
        // Recupera l'ID dell'utente dal DTO anziché dal nome utente
        Long userId = ferieDTO.getUserId();

        // Verifica se l'utente esiste per l'ID specificato
        Optional<User> user = userService.findById(userId);
        if (!user.isPresent()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized request");
        }

        ferieDTO.setUserId(user.get().getId());
        requestService.addFerieRequest(ferieDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(Collections.singletonMap("message", "Ferie request created"));
    }




    @GetMapping("/ferie")
    public ResponseEntity<List<Ferie>> getFerieRequestsByUserId() {
        // Recupera l'ID dell'utente da un contesto sicuro (ad es. attraverso l'autenticazione)
        Long userId = getUserIdFromSecurityContext();

        // Verifica se l'utente esiste per l'ID specificato
        Optional<User> user = userService.findById(userId);
        if (!user.isPresent()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<Ferie> ferieList = requestService.getFerieRequestsByUserId(user.get().getId());
        return ResponseEntity.ok(ferieList);
    }

    @PostMapping("/permessi")
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

    @GetMapping("/permessi")
    public ResponseEntity<List<Permessi>> getPermessiRequestsByUserId() {
        Long userId = getUserIdFromSecurityContext();

        Optional<User> user = userService.findById(userId);
        if (!user.isPresent()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<Permessi> permessiList = requestService.getPermessiRequestsByUserId(user.get().getId());
        return ResponseEntity.ok(permessiList);
    }


    // Metodo di esempio per recuperare l'ID dell'utente da un contesto sicuro (ad es. attraverso l'autenticazione)
    private Long getUserIdFromSecurityContext() {
        // Implementa la logica per recuperare l'ID dell'utente dall'autenticazione o dal contesto sicuro
        // Per esempio, potresti utilizzare Spring Security per ottenere l'ID dell'utente autenticato
        // return ...;
        return 1L; // Implementa la logica reale per ottenere l'ID dell'utente
    }
}
