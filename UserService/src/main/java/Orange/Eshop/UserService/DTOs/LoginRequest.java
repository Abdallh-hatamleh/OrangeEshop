package Orange.Eshop.UserService.DTOs;

import lombok.Data;

@Data
public class LoginRequest {

    private String email;
    private String password;
}
