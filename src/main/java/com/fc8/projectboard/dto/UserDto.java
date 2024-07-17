package com.fc8.projectboard.dto;

import com.fc8.projectboard.domain.User;

import java.time.LocalDateTime;

public record UserDto(
        Long id,
        String userId,
        String password,
        String email,
        String nickname,
        String memo,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {
    public static UserDto of(Long id, String userId, String password, String email, String nickname, String memo) {
        return new UserDto(id, userId, password, email, nickname, memo, null, null, null, null);
    }

    public static UserDto of(Long id, String userId, String password, String email, String nickname, String memo, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new UserDto(id, userId, password, email, nickname, memo, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static UserDto from(User user) {
        return new UserDto(user.getId(),
                user.getUserId(),
                user.getPassword(),
                user.getEmail(),
                user.getNickname(),
                user.getMemo(),
                user.getCreatedAt(),
                user.getCreatedBy(),
                user.getModifiedAt(),
                user.getModifiedBy()
        );
    }

    public User toEntity() {
        return User.of(
                userId,
                password,
                email,
                nickname,
                memo
        );
    }
}
