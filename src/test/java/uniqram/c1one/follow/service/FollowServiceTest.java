package uniqram.c1one.follow.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import uniqram.c1one.follow.entity.Follow;
import uniqram.c1one.follow.exception.FollowErrorCode;
import uniqram.c1one.follow.exception.FollowException;
import uniqram.c1one.follow.repository.FollowRepository;
import uniqram.c1one.user.entity.Role;
import uniqram.c1one.user.entity.Users;
import uniqram.c1one.user.repository.UserRepository;

@SpringBootTest
@Transactional
class FollowServiceTest {

    @Autowired
    private FollowService followService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowRepository followRepository;

    private Users createUser(String username) {
        Users users = Users.builder()
                .username(username)
                .password("password")
                .email(username + "@test.com")
                .role(Role.USER)
                .build();
        return userRepository.save(users);
    }

    @Test
    @DisplayName("성공 - 팔로우 생성")
    void createFollow_success() {
        Users user1 = createUser("user1");
        Users user2 = createUser("user2");

        followService.createFollow(user1.getId(), user2.getId());

        List<Follow> followings = followService.getFollowings(user1.getId());
        assertEquals(1, followings.size());
        assertEquals(user2.getId(), followings.get(0).getFollowing().getId());
    }

    @Test
    @DisplayName("실패 - 팔로우 생성: 존재하지 않는 사용자")
    void createFollow_fail_userNotFound() {
        // when
        FollowException ex = assertThrows(FollowException.class, () ->
                followService.createFollow(999L, 1000L)
        );

        // then
        assertEquals(FollowErrorCode.USER_NOT_FOUND, ex.getErrorCode());
    }

    @Test
    @DisplayName("실패 - 팔로우 생성: 자기 자신 팔로우 시도")
    void createFollow_fail_cannotFollowSelf() {
        // given
        Users user = createUser("myself");

        // when
        FollowException ex = assertThrows(FollowException.class, () ->
                followService.createFollow(user.getId(), user.getId()));

        // then
        assertEquals(FollowErrorCode.CANNOT_FOLLOW_SELF, ex.getErrorCode());
    }

    @Test
    @DisplayName("실패 - 팔로우 생성: 이미 팔로우 한 사용자")
    void createFollow_fail_alreadyFollowing() {
        // given
        Users user1 = createUser("user1");
        Users user2 = createUser("user2");
        followService.createFollow(user1.getId(), user2.getId());

        // when
        FollowException ex = assertThrows(FollowException.class, () ->
                followService.createFollow(user1.getId(), user2.getId()));

        //hen
        assertEquals(FollowErrorCode.ALREADY_FOLLOWING, ex.getErrorCode());
    }

    @Test
    @DisplayName("성공 - 언팔로우")
    void unfollow_success() {
        // given
        Users user1 = createUser("user1");
        Users user2 = createUser("user2");
        followService.createFollow(user1.getId(), user2.getId());

        // when
        followService.unfollow(user1.getId(), user2.getId());

        // then
        assertEquals(0, followService.getFollowings(user1.getId()).size());
    }

    @Test
    @DisplayName("실패 - 언팔로우: 팔로우 관계 없음")
    void unfollow_fail_followNotFound() {
        // given
        Users user1 = createUser("user1");
        Users user2 = createUser("user2");

        // when
        FollowException ex = assertThrows(FollowException.class, () ->
                followService.unfollow(user1.getId(), user2.getId())
        );

        // then
        assertEquals(FollowErrorCode.FOLLOW_NOT_FOUND, ex.getErrorCode());
    }

    @Test
    @DisplayName("성공 - 내 팔로워 강제 삭제")
    void removeFollower_success() {
        // given
        Users user1 = createUser("user1");
        Users user2 = createUser("user2");
        followService.createFollow(user2.getId(), user1.getId()); // user2가 user1을 팔로우

        // when
        followService.removeFollower(user1.getId(), user2.getId());

        // then
        assertEquals(0, followService.getFollowers(user1.getId()).size());
    }

    @Test
    @DisplayName("실패 - 내 팔로워 강제 삭제: 팔로우 관계 없음")
    void removeFollower_fail_followNotFound() {
        // given
        Users user1 = createUser("user1");
        Users user2 = createUser("user2");

        // when
        FollowException ex = assertThrows(FollowException.class, () ->
                followService.removeFollower(user1.getId(), user2.getId())
        );

        // then
        assertEquals(FollowErrorCode.FOLLOW_NOT_FOUND, ex.getErrorCode());
    }

    @Test
    @DisplayName("성공 - 팔로잉 목록 조회")
    void getFollowings_success() {
        // given
        Users user1 = createUser("user1");
        Users user2 = createUser("user2");
        Users user3 = createUser("user3");
        followService.createFollow(user1.getId(), user2.getId());
        followService.createFollow(user1.getId(), user3.getId());

        // when
        List<Follow> followings = followService.getFollowings(user1.getId());

        // then
        assertEquals(2, followings.size());
    }

    @Test
    @DisplayName("성공 - 팔로워 목록 조회")
    void getFollowers_success() {
        // given
        Users user1 = createUser("user1");
        Users user2 = createUser("user2");
        Users user3 = createUser("user3");
        followService.createFollow(user2.getId(), user1.getId());
        followService.createFollow(user3.getId(), user1.getId());

        // when
        List<Follow> followers = followService.getFollowers(user1.getId());

        // then
        assertEquals(2, followers.size());
    }

    @DisplayName("팔로잉 목록 조회시 연관 Users 접근 - N+1 쿼리 발생 여부 확인")
    @Test
    void checkNPlusOneProblemOnGetFollowings() {
        // given
        Users user = userRepository.save(
                Users.builder()
                        .username("main")
                        .password("pw")
                        .email("main@test.com")
                        .role(Role.USER)
                        .build()
        );

        for (int i = 0; i < 10; i++) {
            Users following = userRepository.save(
                    Users.builder()
                            .username("user" + i)
                            .password("pw")
                            .email("user" + i + "@test.com")
                            .role(Role.USER)
                            .build()
            );
            followService.createFollow(user.getId(), following.getId());
        }

        // when
        List<Follow> followings = followService.getFollowings(user.getId());

        // then
        for (Follow follow : followings) {
            System.out.println("팔로잉 유저명: " + follow.getFollowing().getUsername());
        }
    }
}