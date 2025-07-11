package uniqram.c1one.blockeduser.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BlockResponse {
    private Long id;
    private Long blockerUserId;
    private Long blockedUserId;
    private LocalDateTime blockedAt;
}