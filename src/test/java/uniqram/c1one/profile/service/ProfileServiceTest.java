package uniqram.c1one.profile.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import uniqram.c1one.profile.dto.ProfileResponseDto;
import uniqram.c1one.profile.dto.ProfileUpdateRequestDto;
import uniqram.c1one.profile.entity.Profile;
import uniqram.c1one.profile.repository.ProfileRepository;
import uniqram.c1one.user.entity.Role;
import uniqram.c1one.user.entity.Users;
import uniqram.c1one.user.repository.UserRepository;

@SpringBootTest
@Transactional
class ProfileServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ProfileRepository profileRepository;

    private Users testUser;
    private Profile testProfile;

    @BeforeEach
    void setUp() {
        testUser = new Users("testUser", "password", Role.USER);
        userRepository.save(testUser);

        testProfile = Profile.builder()
                .userId(testUser)
                .bio("bio")
                .profileImageUrl("ImageURI")
                .build();
        profileRepository.save(testProfile);
    }

    @Test
    @DisplayName("ProfileService - 프로필 조회 성공")
    void getProfileByIdTest() {
        // given
        Long profileId = testProfile.getId();

        // when
        ProfileResponseDto dto = profileService.getProfileById(profileId);

        // then
        assertNotNull(dto);
        assertEquals("bio", dto.getBio());
        assertEquals("ImageURI", dto.getProfileImageUrl());
        assertEquals(testUser.getId(), dto.getUserId());
    }

    @Test
    @DisplayName("ProfileService - 프로필 업데이트 성공")
    void updateProfileTest() {
        // given
        Long profileId = testProfile.getId();
        ProfileUpdateRequestDto updateDto = new ProfileUpdateRequestDto("수정된 bio", "NewImageURI");

        // when
        ProfileResponseDto updated = profileService.updateProfile(profileId, updateDto);

        // then
        assertEquals("수정된 bio", updated.getBio());
        assertEquals("NewImageURI", updated.getProfileImageUrl());
    }

}