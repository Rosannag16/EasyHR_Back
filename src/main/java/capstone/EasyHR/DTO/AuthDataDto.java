package capstone.EasyHR.DTO;

import capstone.EasyHR.Enums.Ruolo;
import lombok.Data;

@Data
public class AuthDataDto {
    private int id;
    private String accessToken;
    private String username;

    private String email;

    private String nome;

    private String cognome;

//    private String avatar;

    private Ruolo ruolo;
}
