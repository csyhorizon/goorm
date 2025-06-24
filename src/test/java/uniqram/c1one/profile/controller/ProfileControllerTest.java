package uniqram.c1one.profile.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import uniqram.c1one.profile.entity.Profile;
import uniqram.c1one.profile.repository.ProfileRepository;
import uniqram.c1one.profile.service.ProfileService;
import uniqram.c1one.user.entity.Users;

class ProfileControllerTest {

    @InjectMocks
    private ProfileService profileService;

    @Mock
    private ProfileRepository profileRepository;

    @Test
    @DisplayName("프로필 조회 테스트 - 정상")
    void getProfileById() {
        // given

        // when

        // then
    }

}