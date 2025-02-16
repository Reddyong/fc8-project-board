package com.fc8.projectboard.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled(value = "Spring Data REST 통합 테스트는 불필요하므로 제외 시킴")
@DisplayName(value = "Data REST - API test")
@Transactional
@AutoConfigureMockMvc
@SpringBootTest
public class DataRestTest {

    private final MockMvc mockMvc;

    public DataRestTest(
            @Autowired
            MockMvc mockMvc
    ) {
        this.mockMvc = mockMvc;
    }

    @DisplayName(value = "[api] 게시글 리스트 조회 테스트")
    @Test
    void givenNothing_whenRequestingArticles_thenReturnsArticlesResponse() throws Exception {
        // given

        // when, then
        mockMvc.perform(get("/api/articles"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));
    }

    @DisplayName(value = "[api] 게시글 단건 조회 테스트")
    @Test
    void givenNothing_whenRequestingArticle_thenReturnsArticleResponse() throws Exception {
        // given

        // when, then
        mockMvc.perform(get("/api/articles/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));
    }

    @DisplayName(value = "[api] 게시글 -> 댓글 리스트 조회 테스트")
    @Test
    void givenNothing_whenRequestingCommentFromArticle_thenReturnsCommentsResponse() throws Exception {
        // given

        // when, then
        mockMvc.perform(get("/api/articles/1/comments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));
    }

    @DisplayName(value = "[api] 댓글 리스트 조회 테스트")
    @Test
    void givenNothing_whenRequestingComments_thenReturnsCommentsResponse() throws Exception {
        // given

        // when, then
        mockMvc.perform(get("/api/comments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));
    }

    @DisplayName(value = "[api] 댓글 단건 조회 테스트")
    @Test
    void givenNothing_whenRequestingComment_thenReturnsCommentResponse() throws Exception {
        // given

        // when, then
        mockMvc.perform(get("/api/comments/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));
    }

    @DisplayName(value = "[api] 회원 관련 API 는 일체 제공하지 않는다.")
    @Test
    void givenNothing_whenRequestingUserAccounts_thenThrowException() throws Exception {
        // given

        // when, then
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isNotFound());
        mockMvc.perform(post("/api/users"))
                .andExpect(status().isNotFound());
        mockMvc.perform(put("/api/users"))
                .andExpect(status().isNotFound());
        mockMvc.perform(patch("/api/users"))
                .andExpect(status().isNotFound());
        mockMvc.perform(delete("/api/users"))
                .andExpect(status().isNotFound());
        mockMvc.perform(head("/api/users"))
                .andExpect(status().isNotFound());
    }

}
