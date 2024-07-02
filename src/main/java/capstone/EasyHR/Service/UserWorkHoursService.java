package capstone.EasyHR.Service;

import capstone.EasyHR.DTO.UserWorkHoursDTO;
import capstone.EasyHR.Entities.User;
import capstone.EasyHR.Entities.UserWorkHours;
import capstone.EasyHR.Repository.UserRepository;
import capstone.EasyHR.Repository.UserWorkHoursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserWorkHoursService {

    @Autowired
    private UserWorkHoursRepository userWorkHoursRepository; // Repository per le ore lavorative dell'utente

    @Autowired
    private UserRepository userRepository; // Repository per gli utenti

    // Metodo transazionale per aggiungere le ore lavorative di un utente
    @Transactional
    public UserWorkHoursDTO addUserWorkHours(Long userId, UserWorkHoursDTO userWorkHoursDTO) {
        // Recupera l'utente dal repository tramite ID o lancia un'eccezione se non trovato
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Utente non trovato"));

        // Crea un nuovo oggetto UserWorkHours e imposta i dati dalle informazioni fornite dal DTO
        UserWorkHours userWorkHours = new UserWorkHours();
        userWorkHours.setUser(user);
        userWorkHours.setDataLavoro(String.valueOf(userWorkHoursDTO.getDataLavoro())); // Converte LocalDate in stringa
        userWorkHours.setInizioOraLavoro(userWorkHoursDTO.getInizioOraLavoro()); // Imposta l'ora di inizio
        userWorkHours.setFineOraLavoro(userWorkHoursDTO.getFineOraLavoro()); // Imposta l'ora di fine

        // Salva le ore lavorative dell'utente nel repository
        userWorkHoursRepository.save(userWorkHours);

        // Ritorna il DTO originale, utile per feedback o conferma di aggiunta
        return userWorkHoursDTO;
    }

    // Metodo per recuperare tutte le ore lavorative degli utenti
    public List<UserWorkHoursDTO> getAllUserWorkHours() {
        // Recupera tutte le ore lavorative degli utenti dal repository
        List<UserWorkHours> userWorkHoursList = userWorkHoursRepository.findAll();

        // Converte ciascuna entità UserWorkHours in un DTO corrispondente e ritorna la lista
        return userWorkHoursList.stream()
                .map(this::buildUserWorkHoursDTO)
                .collect(Collectors.toList());
    }

    // Metodo privato per convertire un'entità UserWorkHours in un DTO UserWorkHoursDTO
    private UserWorkHoursDTO buildUserWorkHoursDTO(UserWorkHours userWorkHours) {
        UserWorkHoursDTO dto = new UserWorkHoursDTO();

        // Converte la data lavorativa da stringa a LocalDate
        dto.setDataLavoro(LocalDate.parse(userWorkHours.getDataLavoro()));

        // Converte l'ora di inizio e fine da stringa a LocalTime utilizzando un formato specifico
        dto.setInizioOraLavoro(LocalTime.parse(userWorkHours.getInizioOraLavoro().format(DateTimeFormatter.ofPattern("HH:mm:ss"))));
        dto.setFineOraLavoro(LocalTime.parse(userWorkHours.getFineOraLavoro().format(DateTimeFormatter.ofPattern("HH:mm:ss"))));

        return dto; // Ritorna il DTO convertito
    }
}
