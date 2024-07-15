package com.fc8.projectboard.dto.response;

import com.fc8.projectboard.dto.ArticleDto;

import java.time.LocalDateTime;

public record ArticleResponse(
        Long id,
        String title,
        String content,
        String hashtag,
        LocalDateTime createdAt,
        String email,
        String nickname
) {
    public static ArticleResponse of(Long id, String title, String content, String hashtag, LocalDateTime createdAt, String email, String nickname) {
        return new ArticleResponse(id, title, content, hashtag, createdAt, email, nickname);
    }

    public static ArticleResponse from(ArticleDto articleDto) {
        String nickname = articleDto.userDto().nickname();
        if (nickname == null || nickname.isBlank()) {
            nickname = articleDto.userDto().userId();
        }

        return new ArticleResponse(
                articleDto.id(),
                articleDto.title(),
                articleDto.content(),
                articleDto.hashtag(),
                articleDto.createdAt(),
                articleDto.userDto().email(),
                nickname
        );
    }
}
