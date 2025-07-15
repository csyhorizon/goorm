package uniqram.c1one.redis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActiveUser implements Serializable {

    private Long userId;
    private String username;
    private LocalDateTime loginTime;
    private String ipAddress;
    private LocalDateTime lastAccessTime;

}
