package org.novice.learnreactprog.dto;

import lombok.*;
import org.novice.learnreactprog.entity.User;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserResponse {

    private Long id;

    private String name;
    private String email;
    private String role;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static UserResponse from(User user){
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

}
