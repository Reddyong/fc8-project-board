package com.fc8.projectboard.dto.response;

import com.fc8.projectboard.dto.CommentDto;

import java.time.LocalDateTime;

public record CommentResponse(
        Long id,
        String content,
        LocalDateTime createdAt,
        String email,
        String nickname,
        String userId
) {
    public static CommentResponse of(Long id, String content, LocalDateTime createdAt, String email, String nickname, String userId) {
        return new CommentResponse(id, content, createdAt, email, nickname, userId);
    }

    public static CommentResponse from(CommentDto commentDto) {
        String nickname = commentDto.userDto().nickname();
        if (nickname == null || nickname.isBlank()) {
            nickname = commentDto.userDto().userId();
        }

        return new CommentResponse(
                commentDto.id(),
                commentDto.content(),
                commentDto.createdAt(),
                commentDto.userDto().email(),
                nickname,
                commentDto.userDto().userId()
        );
    }
}
