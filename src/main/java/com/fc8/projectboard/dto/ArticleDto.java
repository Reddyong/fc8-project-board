package com.fc8.projectboard.dto;

import com.fc8.projectboard.domain.Article;
import com.fc8.projectboard.domain.User;

import java.time.LocalDateTime;

public record ArticleDto(
        Long id,
        UserDto userDto,
        String title,
        String content,
        String hashtag,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {

    public static ArticleDto of(UserDto userDto, String title, String content, String hashtag) {
        return new ArticleDto(null, userDto, title, content, hashtag, null, null, null, null);
    }

    public static ArticleDto of(Long id, UserDto userDto, String title, String content, String hashtag, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new ArticleDto(id, userDto, title, content, hashtag, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static ArticleDto from(Article article) {
        return new ArticleDto(
                article.getId(),
                UserDto.from(article.getUser()),
                article.getTitle(),
                article.getContent(),
                article.getHashtag(),
                article.getCreatedAt(),
                article.getCreatedBy(),
                article.getModifiedAt(),
                article.getModifiedBy()
        );
    }

    public Article toEntity(User user) {
        return Article.of(
                user,
                title,
                content,
                hashtag
        );
    }
}
