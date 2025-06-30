package uniqram.c1one.profile.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
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
    @DisplayName("프로필 조회 테스트 - 성공")
    void getProfileUserTest() {
        // given
        Long profileId = testProfile.getId();

        // when
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new RuntimeException("프로필이 존재하지 않음"));

        // then
        assertNotNull(profile);
        assertEquals("bio", profile.getBio());
        assertEquals("ImageURI", profile.getProfileImageUrl());
        assertEquals(testUser.getId(), profile.getUserId().getId());
    }
}