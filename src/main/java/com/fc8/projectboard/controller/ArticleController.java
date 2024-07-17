package com.fc8.projectboard.controller;

import com.fc8.projectboard.domain.type.SearchType;
import com.fc8.projectboard.dto.ArticleDto;
import com.fc8.projectboard.dto.ArticleWithCommentsDto;
import com.fc8.projectboard.dto.response.ArticleResponse;
import com.fc8.projectboard.dto.response.ArticleWithCommentResponse;
import com.fc8.projectboard.service.ArticleService;
import com.fc8.projectboard.service.PaginationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping(path = "/articles")
@RequiredArgsConstructor
@Controller
public class ArticleController {

    private final ArticleService articleService;
    private final PaginationService paginationService;

    @GetMapping(path = "")
    public String articles(
            @RequestParam(required = false) SearchType searchType,
            @RequestParam(required = false) String searchValue,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable,
            ModelMap modelMap
    ) {
        Page<ArticleResponse> articles = articleService.searchArticles(searchType, searchValue, pageable).map(ArticleResponse::from);
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), articles.getTotalPages());

        modelMap.addAttribute("articles", articles);
        modelMap.addAttribute("paginationBarNumbers", barNumbers);
        modelMap.addAttribute("searchTypes", SearchType.values());
        return "articles/index";
    }

    @GetMapping(path = "/{article-id}")
    public String articles(
            @PathVariable(name = "article-id")
            Long articleId,
            ModelMap modelMap
    ) {

        ArticleWithCommentsDto articleWithCommentsDto = articleService.getArticle(articleId);
        ArticleWithCommentResponse articleWithCommentResponse = ArticleWithCommentResponse.from(articleWithCommentsDto);

        modelMap.addAttribute("article", articleWithCommentResponse);
        modelMap.addAttribute("comments", articleWithCommentResponse.commentsResponse());
        modelMap.addAttribute("totalCount", articleService.getArticleCount());
        return "articles/detail";
    }

    @GetMapping(path = "/search-hashtag")
    public String searchHashtag(
            @RequestParam(required = false) String searchValue,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable,
            ModelMap modelMap
    ) {
        Page<ArticleResponse> articles = articleService.searchArticlesViaHashtag(searchValue, pageable).map(ArticleResponse::from);
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), articles.getTotalPages());
        List<String> hashtags = articleService.getHashtags();

        modelMap.addAttribute("articles", articles);
        modelMap.addAttribute("paginationBarNumbers", barNumbers);
        modelMap.addAttribute("hashtags", hashtags);
        modelMap.addAttribute("searchType", SearchType.HASHTAG);

        return "articles/search-hashtag";
    }
}
