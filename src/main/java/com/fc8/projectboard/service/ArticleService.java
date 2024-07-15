package com.fc8.projectboard.service;

import com.fc8.projectboard.domain.type.SearchType;
import com.fc8.projectboard.dto.ArticleDto;
import com.fc8.projectboard.dto.ArticleWithCommentsDto;
import com.fc8.projectboard.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticles(SearchType searchType, String searchKeyword, Pageable pageable) {
        return Page.empty();
    }

    @Transactional(readOnly = true)
    public ArticleWithCommentsDto getArticle(Long articleId) {
        return null;
    }

    public void saveArticle(ArticleDto articleDto) {

    }


    public void updateArticle(ArticleDto articleDto) {

    }

    public void deleteArticle(Long articleId) {

    }
}
