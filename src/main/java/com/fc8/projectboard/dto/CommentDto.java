package com.fc8.projectboard.dto;

import com.fc8.projectboard.domain.Article;
import com.fc8.projectboard.domain.Comment;
import com.fc8.projectboard.domain.User;

import java.time.LocalDateTime;

public record CommentDto(
        Long id,
        Long articleId,
        UserDto userDto,
        String content,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {
    public static CommentDto of(Long articleId, UserDto userDto, String content) {
        return new CommentDto(null, articleId, userDto, content, null, null, null, null);
    }

    public static CommentDto of(Long id, Long articleId, UserDto userDto, String content, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new CommentDto(id, articleId, userDto, content, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static CommentDto from(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getArticle().getId(),
                UserDto.from(comment.getUser()),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getCreatedBy(),
                comment.getModifiedAt(),
                comment.getModifiedBy()
        );
    }

    public Comment toEntity(Article article, User user) {
        return Comment.of(
                article,
                user,
                content
        );
    }
}
