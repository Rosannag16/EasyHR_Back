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
    private UserRepository userRepository;

    @Autowired
    private UserWorkHoursRepository userWorkHoursRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String saveUtente(UserDTO userDTO, Ruolo ruolo) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setNome(userDTO.getNome());
        user.setCognome(userDTO.getCognome());
        user.setEmail(userDTO.getEmail());
        user.setRuolo(Ruolo.USER); // Imposta il ruolo passato come parametro
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        userRepository.save(user); // Salva l'utente nel database

        return "User with id=" + user.getId() + " correctly saved";
    }

    public Optional<User> getUtenteById(int id) {
        return userRepository.findById((long) id);
    }

    public Optional<User> getUtenteByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findByUsername(String username) {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null; // Implementa se necessario
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setNome(user.getNome());
        userDTO.setCognome(user.getCognome());


        return userDTO;
    }



}
