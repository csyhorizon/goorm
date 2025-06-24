package uniqram.c1one.profile.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileUpdateRequestDto {

    @Size(max = 255)
    private String bio;

    @Size(max = 255)
    private String profileImageUrl;
}