package com.fc8.projectboard.dto.response;

import com.fc8.projectboard.dto.ArticleWithCommentsDto;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public record ArticleWithCommentResponse(
        Long id,
        String title,
        String content,
        String hashtag,
        LocalDateTime createdAt,
        String email,
        String nickname,
        Set<CommentResponse> commentsResponse
) {
    public static ArticleWithCommentResponse of(Long id, String title, String content, String hashtag, LocalDateTime createdAt, String email, String nickname, Set<CommentResponse> commentResponses) {
        return new ArticleWithCommentResponse(id, title, content, hashtag, createdAt, email, nickname, commentResponses);
    }

    public static ArticleWithCommentResponse from(ArticleWithCommentsDto articleWithCommentsDto) {
        String nickname = articleWithCommentsDto.userDto().nickname();
        if (nickname == null || nickname.isBlank()) {
            nickname = articleWithCommentsDto.userDto().userId();
        }

        return new ArticleWithCommentResponse(
                articleWithCommentsDto.id(),
                articleWithCommentsDto.title(),
                articleWithCommentsDto.content(),
                articleWithCommentsDto.hashtag(),
                articleWithCommentsDto.createdAt(),
                articleWithCommentsDto.userDto().email(),
                nickname,
                articleWithCommentsDto.commentDtos().stream().map(CommentResponse::from)
                        .collect(Collectors.toCollection(LinkedHashSet::new))
        );
    }
}
