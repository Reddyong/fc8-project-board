package com.fc8.projectboard.controller;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("View 컨트롤러 - 게시글")
@WebMvcTest(ArticleController.class)
public class ArticleControllerTest {

    private final MockMvc mockMvc;

    public ArticleControllerTest(
            @Autowired
            MockMvc mockMvc
    ) {
        this.mockMvc = mockMvc;
    }

    @DisplayName("[view][Get] 게시글 리스트 (게시판) 페이지 - 정상 호출")
    @Test
    void givenNothing_whenRequestingArticlesView_thenReturnsArticlesView() throws Exception {
        // given

        // when, then
        mockMvc.perform(get("/articles"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_HTML))
                .andExpect(model().attributeExists("articles"));

    }

    @DisplayName("[view][Get] 게시글 상세 페이지 - 정상 호출")
    @Test
    void givenNothing_whenRequestingArticleView_thenReturnsArticleView() throws Exception {
        // given

        // when, then
        mockMvc.perform(get("/articles/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_HTML))
                .andExpect(model().attributeExists("article"));

    }

    @DisplayName("[view][Get] 게시글 검색 전용 페이지 - 정상 호출")
    @Test
    void givenNothing_whenRequestingArticleSearchView_thenReturnsArticleSearchView() throws Exception {
        // given

        // when, then
        mockMvc.perform(get("/articles/search"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_HTML));

    }

    @DisplayName("[view][Get] 게시글 해시태그 검색 페이지 - 정상 호출")
    @Test
    void givenNothing_whenRequestingArticleHashtagSearchView_thenReturnsArticleHashtagSearchView() throws Exception {
        // given

        // when, then
        mockMvc.perform(get("/articles/search-hashtag"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_HTML));

    }
}