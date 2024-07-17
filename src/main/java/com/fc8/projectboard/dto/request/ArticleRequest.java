package com.fc8.projectboard.dto.request;

import com.fc8.projectboard.dto.ArticleDto;
import com.fc8.projectboard.dto.UserDto;

public record ArticleRequest(
        String title,
        String content,
        String hashtag
) {

    public static ArticleRequest of(String title, String content, String hashtag) {
        return new ArticleRequest(title, content, hashtag);
    }

    public ArticleDto toDto(UserDto userDto) {
        return ArticleDto.of(
                userDto,
                title,
                content,
                hashtag
        );
    }
}
