package com.example.backend.domain.redis.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "@class"
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActiveUser {

    private Long userId;
    private String username;
    private LocalDateTime loginTime;
    private String ipAddress;
    private LocalDateTime lastAccessTime;
}
