package uniqram.c1one.profile.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import uniqram.c1one.profile.entity.Profile;

@Getter
@AllArgsConstructor
@Builder
public class ProfileResponseDto {

    private Long id;
    private Long userId;

    @Size(max = 255)
    private String bio;

    @Size(max = 255)
    private String profileImageUrl;

    public static ProfileResponseDto from(Profile profile) {
        return ProfileResponseDto.builder()
                .id(profile.getId())
                .userId(profile.getUserId().getId())
                .bio(profile.getBio())
                .profileImageUrl(profile.getProfileImageUrl())
                .build();
    }
}
