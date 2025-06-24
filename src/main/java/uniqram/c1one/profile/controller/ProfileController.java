package uniqram.c1one.profile.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uniqram.c1one.profile.dto.ProfileResponseDto;
import uniqram.c1one.profile.dto.ProfileUpdateRequestDto;
import uniqram.c1one.profile.service.ProfileService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/{userId}/profile")
    public ProfileResponseDto getProfile(@PathVariable Long userId) {
        return profileService.getProfileById(userId);
    }


    @PatchMapping("/{userId}/profile")
    public ProfileResponseDto patchProfile(
            @PathVariable Long userId,
            @Valid @RequestBody ProfileUpdateRequestDto profileUpdateRequestDto
    ) {
        return profileService.updateProfile(userId, profileUpdateRequestDto);
    }
}
