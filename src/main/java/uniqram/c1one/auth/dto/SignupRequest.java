package uniqram.c1one.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uniqram.c1one.user.entity.Role;

@Getter
@NoArgsConstructor
public class SignupRequest {

    @NotBlank
    @Size(min = 2, max = 20)
    private String username;

    @NotBlank
    @Size(min = 6, max = 100)
    private String password;

    @NotBlank
    private String confirmPassword;

    @NotBlank
    @Email
    private String email;

    private Role role;

    @Builder
    public SignupRequest(String username, String password, String confirmPassword, String email, Role role) {
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.email = email;
        this.role = role;
    }
}