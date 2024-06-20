//package capstone.EasyHR.Controller;
//
//import capstone.EasyHR.DTO.UserDTO;
//import capstone.EasyHR.Entities.User;
//import capstone.EasyHR.Enums.Ruolo;
//import capstone.EasyHR.Service.UserService;
//import jakarta.validation.Valid;
//import org.apache.coyote.BadRequestException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/admin")
//public class AdminController {
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    // Endpoint per creare l'admin con credenziali predefinite
//    @PostMapping("/create")
//    public String register(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) throws BadRequestException {
//        if (bindingResult.hasErrors()) {
//            throw new BadRequestException(bindingResult.getAllErrors().stream()
//                    .map(objectError -> objectError.getDefaultMessage())
//                    .reduce("", (s, s2) -> s + " " + s2));
//        }
//        return userService.saveUtente(userDTO, Ruolo.ADMIN);
//    }
//}
