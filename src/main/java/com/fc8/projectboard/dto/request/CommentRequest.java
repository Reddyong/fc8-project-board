package com.fc8.projectboard.dto.request;

import com.fc8.projectboard.dto.CommentDto;
import com.fc8.projectboard.dto.UserDto;

public record CommentRequest(
        Long articleId,
        String content
) {
    public static CommentRequest of(Long articleId, String content) {
        return new CommentRequest(articleId, content);
    }

    public CommentDto toDto(UserDto userDto) {
        return CommentDto.of(
                articleId,
                userDto,
                content
        );
    }
}
