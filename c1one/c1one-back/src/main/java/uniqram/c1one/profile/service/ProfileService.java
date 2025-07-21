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

    /**
     * 프로필 생성
     *
     * @param userId 기반의 프로필 생성
     * @return 저장된 프로필 정보
     */
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

    /**
     * UserId 기반 프로필 조회
     *
     * @param userId 프로필을 조회할 사용자의 ID
     * @return 조회된 프로필의 응답 DTO
     */
    @Transactional(readOnly = true)
    public ProfileResponseDto getProfileByUserId(Long userId) {

        final Profile profile = profileRepository.findProfileWithUserByUserId(userId)
                .orElseThrow(() -> new ProfileException(ProfileErrorCode.PROFILE_NOT_FOUND));

        return ProfileResponseDto.from(profile);
    }

    /**
     * 사용자 ID 기반 이미지 수정
     *
     * @param userId 프로필 이미지를 업데이트할 사용자의 ID
     * @param profileImage 새로운 프로필 이미지 파일
     * @return 업데이트된 프로필의 응답 DTO
     */
    @Transactional
    public ProfileResponseDto updateProfileImage(Long userId, MultipartFile profileImage) {
        // userId를 사용하여 프로필을 찾습니다.
        Profile profile = profileRepository.findByUserId_Id(userId)
                .orElseThrow(() -> new ProfileException(ProfileErrorCode.PROFILE_NOT_FOUND));

        if (profile.getProfileImageUrl() != null) {
            s3Service.deleteFile(profile.getProfileImageUrl());
        }

        String fileName = s3Service.uploadFile(profileImage);
        profile.update(profile.getBio(), fileName);

        return ProfileResponseDto.from(profile);
    }

    /**
     * 사용자 ID를 기반으로 프로필 정보를 업데이트
     *
     * @param userId 프로필 정보를 업데이트할 사용자의 ID
     * @param profileUpdateRequestDto 업데이트할 프로필 정보 DTO
     * @return 업데이트된 프로필의 응답 DTO
     */
    @Transactional
    public ProfileResponseDto updateProfile(Long userId, ProfileUpdateRequestDto profileUpdateRequestDto) { // profileId -> userId
        // userId를 사용하여 프로필을 찾습니다.
        Profile profile = profileRepository.findByUserId_Id(userId) // findById -> findByUserId_Id
                .orElseThrow(() -> new ProfileException(ProfileErrorCode.PROFILE_NOT_FOUND));

        profile.update(profileUpdateRequestDto.getBio(), profileUpdateRequestDto.getProfileImageUrl());

        return ProfileResponseDto.from(profile);
    }
}