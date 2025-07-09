package uniqram.c1one.profile.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uniqram.c1one.profile.dto.ProfileResponseDto;
import uniqram.c1one.profile.dto.ProfileUpdateRequestDto;
import uniqram.c1one.profile.entity.Profile;
import uniqram.c1one.profile.exception.ProfileErrorCode;
import uniqram.c1one.profile.exception.ProfileException;
import uniqram.c1one.profile.repository.ProfileRepository;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;

    @Transactional(readOnly = true)
    public ProfileResponseDto getProfileById(Long profileId) {

        final Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new ProfileException(ProfileErrorCode.PROFILE_NOT_FOUND));

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
