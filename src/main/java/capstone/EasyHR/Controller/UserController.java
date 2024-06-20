package capstone.EasyHR.Controller;


//import capstone.EasyHR.DTO.UpdateUserWorkHoursDTO;
import capstone.EasyHR.DTO.UpdateUserWorkHoursDTO;
import capstone.EasyHR.Entities.User;
import capstone.EasyHR.Repository.UserRepository;
import capstone.EasyHR.Service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Endpoint per eliminare tutti gli utenti
    @DeleteMapping
    public String deleteAllUsers() {
        userRepository.deleteAll();
        return "Tutti gli utenti sono stati eliminati";
    }

    // Metodo per aggiornare le ore di lavoro di un utente
    @PutMapping("/{userId}/work-hours")
    public ResponseEntity<User> updateUserWorkHours(
            @PathVariable Integer userId,
            @RequestBody UpdateUserWorkHoursDTO updateUserWorkHoursDTO) {

        User updatedUser = userService.updateUserWorkHours(userId, updateUserWorkHoursDTO);

        // Ritorna una ResponseEntity con l'utente aggiornato e lo stato OK (200)
        return ResponseEntity.ok(updatedUser);
    }

}
