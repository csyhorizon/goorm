package uniqram.c1one.profile.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import uniqram.c1one.global.s3.S3Service;
import uniqram.c1one.profile.dto.ProfileResponseDto;
import uniqram.c1one.profile.dto.ProfileUpdateRequestDto;
import uniqram.c1one.profile.entity.Profile;
import uniqram.c1one.profile.exception.ProfileErrorCode;
import uniqram.c1one.profile.exception.ProfileException;
import uniqram.c1one.profile.repository.ProfileRepository;
import uniqram.c1one.user.entity.Users;
import uniqram.c1one.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final S3Service s3Service;
    private final UserRepository userRepository;

    @Transactional
    public ProfileResponseDto createProfile(Long userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new ProfileException(ProfileErrorCode.USER_NOT_FOUND));
        final Profile profile = Profile.builder()
                .userId(user)
                .bio("")
                .profileImageUrl(null)
                .build();
        final Profile savedProfile = profileRepository.save(profile);
        return ProfileResponseDto.from(savedProfile);
    }

    @Transactional(readOnly = true)
    public ProfileResponseDto getProfileById(Long profileId) {

        final Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new ProfileException(ProfileErrorCode.PROFILE_NOT_FOUND));

        return ProfileResponseDto.from(profile);
    }

    @Transactional
    public ProfileResponseDto updateProfileImage(Long profileId, MultipartFile profileImage) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new ProfileException(ProfileErrorCode.PROFILE_NOT_FOUND));

        if (profile.getProfileImageUrl() != null) {
            s3Service.deleteFile(profile.getProfileImageUrl());
        }

        String fileName = s3Service.uploadFile(profileImage);
        profile.update(profile.getBio(), fileName);

        return ProfileResponseDto.from(profile);
    }

    @Transactional
    public ProfileResponseDto updateProfile(Long profileId, ProfileUpdateRequestDto profileUpdateRequestDto) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new ProfileException(ProfileErrorCode.PROFILE_NOT_FOUND));

        profile.update(profileUpdateRequestDto.getBio(), profileUpdateRequestDto.getProfileImageUrl());

        return ProfileResponseDto.from(profile);
    }
}
