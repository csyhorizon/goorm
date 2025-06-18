package uniqram.c1one.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignupRequest {
    private String username;
    private String password;
    private String confirmPassword;
    private String email;
}
