package com.fc8.projectboard.service;

import com.fc8.projectboard.domain.Article;
import com.fc8.projectboard.domain.Comment;
import com.fc8.projectboard.domain.User;
import com.fc8.projectboard.dto.CommentDto;
import com.fc8.projectboard.dto.UserDto;
import com.fc8.projectboard.repository.ArticleRepository;
import com.fc8.projectboard.repository.CommentRepository;
import com.fc8.projectboard.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@DisplayName("비즈니스 로직 - 댓글")
@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private UserRepository userRepository;

    @DisplayName(value = "게시글 ID 로 조회하면, 해당하는 댓글 리스트를 반환한다.")
    @Test
    void givenArticleId_whenSearchComments_thenReturnsComments() {
        // given
        Long articleId = 1L;
        Comment expected = createComment("content");

        given(commentRepository.findByArticleId(articleId)).willReturn(List.of(expected));

        // when
        List<CommentDto> actual = commentService.searchComments(articleId);

        // then
        assertThat(actual).hasSize(1).first().hasFieldOrPropertyWithValue("content", expected.getContent());
        then(commentRepository).should().findByArticleId(articleId);

    }

    @DisplayName(value = "댓글 정보를 입력하면, 댓글을 저장한다.")
    @Test
    void givenCommentInfo_whenSavingComment_thenSavesComment() {
        // given
        CommentDto dto = createCommentDto("댓글");
        given(articleRepository.getReferenceById(dto.articleId())).willReturn(createArticle());
        given(userRepository.getReferenceById(dto.userDto().id())).willReturn(createUser());
        given(commentRepository.save(any(Comment.class))).willReturn(null);

        // when
        commentService.saveComment(dto);

        // then
        then(articleRepository).should().getReferenceById(dto.articleId());
        then(userRepository).should().getReferenceById(dto.userDto().id());
        then(commentRepository).should().save(any(Comment.class));

    }

    @DisplayName(value = "댓글 저장을 시도했는데 맞는 게시글이 없으면, 경고 로그를 찍고 아무것도 안 한다.")
    @Test
    void givenNonexistentArticle_whenSavingComment_thenLogsSituationAndDoesNothing() {
        // given
        CommentDto dto = createCommentDto("댓글");
        given(articleRepository.getReferenceById(dto.articleId())).willThrow(EntityNotFoundException.class);

        // when
        commentService.saveComment(dto);

        // then
        then(articleRepository).should().getReferenceById(dto.articleId());
        then(userRepository).shouldHaveNoInteractions();
        then(commentRepository).shouldHaveNoInteractions();

    }

    @DisplayName(value = "댓글 정보를 입력하면, 댓글을 수정한다.")
    @Test
    void givenCommentInfo_whenUpdatingComment_thenUpdatesComment() {
        // given
        String oldContent = "content";
        String updatedContent = "댓글";
        Comment comment = createComment(oldContent);
        CommentDto dto = createCommentDto(updatedContent);

        given(commentRepository.getReferenceById(dto.id())).willReturn(comment);

        // when
        commentService.updateComment(dto);

        // then
        assertThat(comment.getContent())
                .isNotEqualTo(oldContent)
                .isEqualTo(updatedContent);

        then(commentRepository).should().getReferenceById(dto.id());

    }

    @DisplayName(value = "없는 댓글 정보를 수정하려고 하면, 경고 로그를 찍고 아무것도 안한다.")
    @Test
    void givenNonexistentComment_whenUpdatingComment_thenLogsWarningAndDoesNothing() {
        // given
        CommentDto dto = createCommentDto("댓글");
        given(commentRepository.getReferenceById(dto.id())).willThrow(EntityNotFoundException.class);

        // when
        commentService.updateComment(dto);

        // then
        then(commentRepository).should().getReferenceById(dto.id());

    }

    @DisplayName(value = "댓글 ID 를 입력하면, 댓글을 삭제한다.")
    @Test
    void givenCommentId_whenDeletingComment_thenDeletesComment() {
        // given
        Long commentId = 1L;
        willDoNothing().given(commentRepository).deleteById(commentId);

        // when
        commentService.deleteComment(commentId);

        // then
        then(commentRepository).should().deleteById(commentId);

    }

    private CommentDto createCommentDto(String content) {
        return CommentDto.of(
                1L,
                1L,
                createUserDto(),
                content,
                LocalDateTime.now(),
                "reddyong",
                LocalDateTime.now(),
                "reddyong"
        );
    }

    private UserDto createUserDto() {
        return UserDto.of(
                1L,
                "reddyong",
                "password",
                "reddyong@email.com",
                "reddyong",
                "memo",
                LocalDateTime.now(),
                "reddyong",
                LocalDateTime.now(),
                "reddyong"
        );
    }

    private Comment createComment(String content) {
        return Comment.of(
                Article.of(createUser(), "title", "content", "hashtag"),
                createUser(),
                content
        );
    }

    private User createUser() {
        return User.of(
                "reddyong",
                "password",
                "reddyong@email.com",
                "reddyong",
                "memo"
        );
    }

    private Article createArticle() {
        return Article.of(
                createUser(),
                "title",
                "content",
                "#java"
        );
    }

}