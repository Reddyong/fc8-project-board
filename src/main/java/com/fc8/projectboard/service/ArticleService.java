package com.fc8.projectboard.service;

import com.fc8.projectboard.domain.Article;
import com.fc8.projectboard.domain.User;
import com.fc8.projectboard.domain.constant.SearchType;
import com.fc8.projectboard.dto.ArticleDto;
import com.fc8.projectboard.dto.ArticleWithCommentsDto;
import com.fc8.projectboard.repository.ArticleRepository;
import com.fc8.projectboard.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticles(SearchType searchType, String searchKeyword, Pageable pageable) {
        if (searchKeyword == null || searchKeyword.isBlank()) {
            return articleRepository.findAll(pageable).map(ArticleDto::from);
        }

        return switch (searchType) {
            case TITLE -> articleRepository.findByTitleContaining(searchKeyword, pageable).map(ArticleDto::from);
            case CONTENT -> articleRepository.findByContentContaining(searchKeyword, pageable).map(ArticleDto::from);
            case ID -> articleRepository.findByUserUserIdContaining(searchKeyword, pageable).map(ArticleDto::from);
            case NICKNAME ->
                    articleRepository.findByUserNicknameContaining(searchKeyword, pageable).map(ArticleDto::from);
            case HASHTAG -> articleRepository.findByHashtag("#" + searchKeyword, pageable).map(ArticleDto::from);
        };

    }

    @Transactional(readOnly = true)
    public ArticleWithCommentsDto getArticleWithComments(Long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleWithCommentsDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다 - articleId: " + articleId));
    }

    @Transactional(readOnly = true)
    public ArticleDto getArticle(Long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다 - articleId: " + articleId));
    }

    public void saveArticle(ArticleDto articleDto) {
        User user = userRepository.getReferenceById(articleDto.userDto().id());

        articleRepository.save(articleDto.toEntity(user));
    }


    public void updateArticle(Long articleId, ArticleDto articleDto) {
        try {
            Article article = articleRepository.getReferenceById(articleId);
            User user = userRepository.getReferenceById(articleDto.userDto().id());

            if (article.getUser().equals(user)) {
                if (articleDto.title() != null) {
                    article.setTitle(articleDto.title());
                }

                if (articleDto.content() != null) {
                    article.setContent(articleDto.content());
                }

                article.setHashtag(articleDto.hashtag());
            }
        } catch (EntityNotFoundException e) {
            log.warn("게시글 업데이트 실패. 게시글을 수정하는데 필요한 정보를 찾을 수 없습니다. - {}", e.getLocalizedMessage());
        }

    }

    public void deleteArticle(Long articleId, String userId) {
        articleRepository.deleteByIdAndUserUserId(articleId, userId);
    }

    @Transactional(readOnly = true)
    public long getArticleCount() {
        return articleRepository.count();
    }

    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticlesViaHashtag(String hashtag, Pageable pageable) {

        if (hashtag == null || hashtag.isBlank()) {
            return Page.empty(pageable);
        }

        return articleRepository.findByHashtag(hashtag, pageable).map(ArticleDto::from);
    }

    @Transactional(readOnly = true)
    public List<String> getHashtags() {
        return articleRepository.findAllDistinctHashtags();
    }
}
