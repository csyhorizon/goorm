package uniqram.c1one.blockeduser.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uniqram.c1one.blockeduser.dto.BlockResponse;
import uniqram.c1one.blockeduser.service.BlockService;
import uniqram.c1one.security.adapter.CustomUserDetails;

@RestController
@RequestMapping("/api/users/")
@RequiredArgsConstructor
public class BlockController {

    private final BlockService blockService;

    @PostMapping("/{userId}/block")
    public ResponseEntity<BlockResponse> blockUser(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable("userId") Long targetUserId
            ) {
        final BlockResponse response = blockService.blockUser(userDetails.getUserId(), targetUserId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{userId}/unblock")
    public ResponseEntity<Void> unblockUser(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable("userId") Long targetUserId
    ) {
        blockService.unblockUser(userDetails.getUserId(), targetUserId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/block")
    public ResponseEntity<List<BlockResponse>> getBlockedUsers(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        final List<BlockResponse> blockResponses = blockService.getBlockedUsers(userDetails.getUserId());
        return ResponseEntity.ok(blockResponses);
    }
}
