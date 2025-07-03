package uniqram.c1one.admin.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder

public class DashboardResponse {
    private long userCount;
    private long postCount;
    private long commentCount;
    private long postLikeCount;
    private long commentLikeCount;
}
