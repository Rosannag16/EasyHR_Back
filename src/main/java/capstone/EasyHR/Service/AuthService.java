package capstone.EasyHR.Service;

import capstone.EasyHR.DTO.AuthDataDto;
import capstone.EasyHR.DTO.UserLoginDTO;
import capstone.EasyHR.Entities.User;
import capstone.EasyHR.Exceptions.NotFoundException;
import capstone.EasyHR.Exceptions.UnauthorizedException;
import capstone.EasyHR.Security.JwtTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTool jwtTool;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthDataDto authenticateUtenteAndCreateToken(UserLoginDTO userLoginDTO) {
        Optional<User> userOptional = userService.getUtenteByEmail(userLoginDTO.getEmail());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword())) {
                AuthDataDto authDataDto = new AuthDataDto();
                authDataDto.setAccessToken(jwtTool.createToken(user));
                authDataDto.setRuolo(user.getRuolo());
                authDataDto.setNome(user.getNome());
                authDataDto.setCognome(user.getCognome());
                authDataDto.setEmail(user.getEmail());
                authDataDto.setUsername(user.getUsername());
                authDataDto.setId(Math.toIntExact(user.getId()));
//                authDataDto.setAvatar(user.getAvatar());
                return authDataDto;
            } else {
                throw new UnauthorizedException("Errore nel login, riloggarsi");
            }
        } else {
            throw new NotFoundException("Utente con email " + userLoginDTO.getEmail() + " non trovato");
        }
    }
}
