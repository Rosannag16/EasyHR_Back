package capstone.EasyHR.Service;

import capstone.EasyHR.DTO.UserDTO;
import capstone.EasyHR.Entities.User;
import capstone.EasyHR.Enums.Ruolo;
import capstone.EasyHR.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import capstone.EasyHR.Repository.UserWorkHoursRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository; // Repository per gli utenti

    @Autowired
    private UserWorkHoursRepository userWorkHoursRepository; // Repository per le ore lavorative degli utenti

    @Autowired
    private PasswordEncoder passwordEncoder; // Encoder per la password degli utenti

    // Metodo per salvare un utente nel database
    public String saveUtente(UserDTO userDTO, Ruolo ruolo) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setNome(userDTO.getNome());
        user.setCognome(userDTO.getCognome());
        user.setEmail(userDTO.getEmail());
        user.setRuolo(Ruolo.USER); // Imposta il ruolo passato come parametro
        user.setPassword(passwordEncoder.encode(userDTO.getPassword())); // Codifica e imposta la password

        userRepository.save(user); // Salva l'utente nel database

        return "Utente con ID=" + user.getId() + " salvato correttamente";
    }

    // Metodo per recuperare un utente per ID
    public Optional<User> getUtenteById(int id) {
        return userRepository.findById((long) id);
    }

    // Metodo per recuperare un utente per email
    public Optional<User> getUtenteByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Metodo richiesto dall'interfaccia UserDetailsService, da implementare se necessario
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null; // Implementare se necessario, per il caricamento dell'utente per username
    }

    // Metodo per recuperare tutti gli utenti e convertirli in DTO
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Metodo per eliminare un utente per ID
    public void deleteUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Utente non trovato con ID: " + userId));

        userRepository.delete(user); // Elimina l'utente dal database
    }

    // Metodo per recuperare un utente per ID
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    // Metodo privato per convertire un'entità User in DTO UserDTO
    public UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setNome(user.getNome());
        userDTO.setCognome(user.getCognome());

        return userDTO; // Ritorna il DTO convertito
    }

}
