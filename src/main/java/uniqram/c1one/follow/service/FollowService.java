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

    @Transactional
    public Follow createFollow(Long followerId, Long followingId) {
        final Users follower = userRepository.findById(followerId).orElseThrow();
        final Users following = userRepository.findById(followingId).orElseThrow();
        final Follow follow = new Follow(follower, following);
        return followRepository.save(follow);
    }

    @Transactional
    public void deleteFollow(Long followerId, Long followingId) {
        final Users follower = userRepository.findById(followerId)
                .orElseThrow(() -> new FollowException(FollowErrorCode.USER_NOT_FOUND));
        final Users following = userRepository.findById(followingId)
                .orElseThrow(() -> new FollowException(FollowErrorCode.USER_NOT_FOUND));
        final Follow follow = followRepository.findByFollowerAndFollowing(follower, following)
                .orElseThrow(() -> new FollowException(FollowErrorCode.FOLLOW_NOT_FOUND));

        followRepository.delete(follow);
    }

    @Transactional(readOnly = true)
    public List<Follow> getFollowings(Long userId) {
        return followRepository.findByFollower_Id(userId);
    }

    @Transactional(readOnly = true)
    public List<Follow> getFollowers(Long userId) {
        return followRepository.findByFollowing_Id(userId);
    }
}
