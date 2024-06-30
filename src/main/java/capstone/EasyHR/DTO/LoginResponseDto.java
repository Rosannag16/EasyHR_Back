package capstone.EasyHR.DTO;


public class LoginResponseDto {
    private String message;
    private AuthDataDto authData;

    public LoginResponseDto(String message, AuthDataDto authData) {
        this.message = message;
        this.authData = authData;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AuthDataDto getAuthData() {
        return authData;
    }

    public void setAuthData(AuthDataDto authData) {
        this.authData = authData;
    }
}
