package uniqram.c1one.follow.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uniqram.c1one.follow.entity.Follow;
import uniqram.c1one.follow.exception.FollowErrorCode;
import uniqram.c1one.follow.exception.FollowException;
import uniqram.c1one.follow.repository.FollowRepository;
import uniqram.c1one.user.entity.Users;
import uniqram.c1one.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    /**
     * 팔로우 생성 기능
     * <p>
     * 두 사용자의 팔로우 관계를 생성합니다
     *
     * @param followerId    팔로우 요청자(팔로워)의 사용자 ID
     * @param followingId   팔로우 대상(팔로잉)의 사용자 ID
     * @throws FollowException 다음과 같은 경우 오류 발생:
     * <ul>
     *  <li>팔로우 요청자 또는 대상 사용자가 존재하지 않을 때</li>
     *  <li>자기 자신을 팔로우하려 할 때</li>
     *  <li>이미 팔로우 관계가 존재할 때</li>
     * </ul>
     */
    @Transactional
    public void createFollow(Long followerId, Long followingId) {

        final Users follower = userRepository.findById(followerId)
                .orElseThrow(() -> new FollowException(FollowErrorCode.USER_NOT_FOUND));
        final Users following = userRepository.findById(followingId)
                .orElseThrow(() -> new FollowException(FollowErrorCode.USER_NOT_FOUND));

        if (follower.equals(following)) {
            throw new FollowException(FollowErrorCode.CANNOT_FOLLOW_SELF);
        }

        if (followRepository.existsByFollowerAndFollowing(follower, following)) {
            throw new FollowException(FollowErrorCode.ALREADY_FOLLOWING);
        }

        followRepository.save(new Follow(follower, following));
    }

    /**
     * 언팔로우(내가 남을 언팔로우) 기능
     * <p>
     * 로그인한 사용자가 특정 사용자를 언팔로우(팔로우 관계 삭제)
     *
     * @param myId              언팔로우 요청하는 사용자의 ID
     * @param targetId          언팔로우 대상 사용자의 ID
     * @throws FollowException  팔로우 관계가 존재하지 않은 경우 발생
     */
    @Transactional
    public void unfollow(Long myId, Long targetId) {
        deleteFollowRelation(myId, targetId);
    }

    /**
     * 팔로워 강제 삭제(내 팔로워를 삭제)하는 기능
     * <p>
     * 로그인한 사용자가 자신의 팔로워 중 특정 사용자를 강제로 삭제
     *
     * @param myId              로그인한 사용자의 ID
     * @param followerId        삭제할 팔로워 사용자의 ID
     * @throws FollowException  팔로워 관계가 존재하지 않은 경우 발생
     */
    @Transactional
    public void removeFollower(Long myId, Long followerId) {
        deleteFollowRelation(followerId, myId);
    }

    /**
     * 팔로잉 목록 조회 기능
     * <p>
     * 특정 사용자가 팔로우하고 있는 사용자 목록을 조회합니다
     * <p>
     * TODO: 추후 DTO 변환 로직으로 개선 예정
     *
     * @param userId 팔로잉 목록을 조회할 사용자의 ID
     * @return 해당 사용자가 팔로우하고 있는 Follow 엔티티 리스트
     */
    @Transactional(readOnly = true)
    public List<Follow> getFollowings(Long userId) {
        return followRepository.findByFollower_Id(userId);
    }

    /**
     * 팔로워 목록 조회 기능
     * <p>
     * 특정 사용자를 팔로우하고 있는 사용자 목록을 조회합니다
     * <p>
     * TODO: 추후 DTO 변환 로직으로 개선 예정
     *
     * @param userId 팔로워 목록을 조회할 사용자의 ID
     * @return 해당 사용자를 팔로우하고 있는 Follow 엔티티 리스트
     */
    @Transactional(readOnly = true)
    public List<Follow> getFollowers(Long userId) {
        return followRepository.findByFollowing_Id(userId);
    }

    private void deleteFollowRelation(Long followerId, Long followingId) {
        int deleted = followRepository.deleteByFollowerIdAndFollowingId(followerId, followingId);
        if (deleted == 0) throw new FollowException(FollowErrorCode.FOLLOW_NOT_FOUND);
    }
}
