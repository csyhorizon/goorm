package uniqram.c1one.profile.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import uniqram.c1one.profile.dto.ProfileResponseDto;
import uniqram.c1one.profile.dto.ProfileUpdateRequestDto;
import uniqram.c1one.profile.service.ProfileService;
import uniqram.c1one.security.adapter.CustomUserDetails;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/profiles/{userId}")
    public ProfileResponseDto getProfile(@PathVariable Long userId) {
        return profileService.getProfileById(userId);
    }

    @PatchMapping("/profiles/image")
    public ProfileResponseDto updateProfileImage(
            @RequestPart("profileImage") MultipartFile profileImage,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return profileService.updateProfileImage(userDetails.getUserId(), profileImage);
    }


    @PatchMapping("/profiles")
    public ProfileResponseDto patchProfile(
            @Valid @RequestBody ProfileUpdateRequestDto profileUpdateRequestDto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return profileService.updateProfile(userDetails.getUserId(), profileUpdateRequestDto);
    }

    @PostMapping("/profiles")
    @ResponseStatus(HttpStatus.CREATED)
    public ProfileResponseDto createProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return profileService.createProfile(userDetails.getUserId());
    }
}
