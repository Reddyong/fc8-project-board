package com.fc8.projectboard.controller;

import com.fc8.projectboard.config.SecurityConfig;
import com.fc8.projectboard.dto.CommentDto;
import com.fc8.projectboard.dto.request.CommentRequest;
import com.fc8.projectboard.service.CommentService;
import com.fc8.projectboard.util.FormDataEncoder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@DisplayName(value = "View 컨트롤러 - 댓글")
@Import(value = {SecurityConfig.class, FormDataEncoder.class})
@WebMvcTest(CommentController.class)
class CommentControllerTest {

    private final MockMvc mockMvc;
    private final FormDataEncoder formDataEncoder;

    @MockBean
    private CommentService commentService;

    public CommentControllerTest(
            @Autowired
            MockMvc mockMvc,
            @Autowired
            FormDataEncoder formDataEncoder
    ) {
        this.mockMvc = mockMvc;
        this.formDataEncoder = formDataEncoder;
    }

    @DisplayName(value = "[view][POST] 댓글 등록 - 정상 호출")
    @Test
    void givenCommentInfo_whenRequesting_thenSavesNewComment() throws Exception {
        // given
        Long articleId = 1L;
        CommentRequest request = CommentRequest.of(articleId, "test content");

        willDoNothing().given(commentService).saveComment(any(CommentDto.class));

        // when, then
        mockMvc.perform(post("/comments/new")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(formDataEncoder.encode(request))
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/articles/" + articleId))
                .andExpect(redirectedUrl("/articles/" + articleId));

        then(commentService).should().saveComment(any(CommentDto.class));
    }

    @DisplayName(value = "[view][POST]")
    @Test
    void givenCommentIdToDelete_whenRequesting_thenDeletesComment() throws Exception {
        // given
        Long articleId = 1L;
        Long commentId = 1L;

        willDoNothing().given(commentService).deleteComment(commentId);

        // when, then
        mockMvc.perform(post("/comments/" + commentId + "/delete")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(formDataEncoder.encode(Map.of("articleId", articleId)))
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/articles/" + articleId))
                .andExpect(redirectedUrl("/articles/" + articleId));

        then(commentService).should().deleteComment(commentId);

    }

}