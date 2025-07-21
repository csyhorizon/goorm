package uniqram.c1one.blockeduser.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uniqram.c1one.blockeduser.dto.BlockResponse;
import uniqram.c1one.blockeduser.entity.BlockedUser;
import uniqram.c1one.blockeduser.repository.BlockRepository;
import uniqram.c1one.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class BlockService {

    private final BlockRepository blockRepository;
    private final UserRepository userRepository;

    @Transactional
    public BlockResponse blockUser(Long blockerUserId, Long targetUserId) {
        if (blockerUserId.equals(targetUserId)) {
            throw new IllegalArgumentException("자신을 차단할 수 없습니다.");
        }

        Optional<BlockedUser> existingBlock = blockRepository.findByBlockerUserIdAndBlockedUserId(blockerUserId, targetUserId);
        if (existingBlock.isPresent()) {
            throw new IllegalArgumentException("이미 차단된 사용자입니다.");
        }

        BlockedUser blockedUser = BlockedUser.builder()
                .blockerUserId(blockerUserId)
                .blockedUserId(targetUserId)
                .build();

        BlockedUser savedBlock = blockRepository.save(blockedUser);

        return new BlockResponse(
                savedBlock.getId(),
                savedBlock.getBlockerUserId(),
                savedBlock.getBlockedUserId(),
                savedBlock.getCreated_at()
        );
    }


    @Transactional
    public void unblockUser(Long blockerUserId, Long targetUserId) {
        Optional<BlockedUser> existingBlock = blockRepository.findByBlockerUserIdAndBlockedUserId(blockerUserId, targetUserId);
        if (existingBlock.isEmpty()) {
            throw new IllegalArgumentException("차단되지 않은 사용자입니다.");
        }

        blockRepository.deleteByBlockerUserIdAndBlockedUserId(blockerUserId, targetUserId);
    }


    @Transactional
    public List<BlockResponse> getBlockedUsers(Long blockerUserId) {
        List<BlockedUser> blockedUsers = blockRepository.findByBlockerUserId(blockerUserId);

        return blockedUsers.stream()
                .map(block -> new BlockResponse(
                        block.getId(),
                        block.getBlockerUserId(),
                        block.getBlockerUserId(),
                        block.getCreated_at()
                ))
                .collect(Collectors.toList());
    }
}
