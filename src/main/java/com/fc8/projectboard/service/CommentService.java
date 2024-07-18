package com.fc8.projectboard.service;

import com.fc8.projectboard.domain.Article;
import com.fc8.projectboard.domain.Comment;
import com.fc8.projectboard.domain.User;
import com.fc8.projectboard.dto.CommentDto;
import com.fc8.projectboard.repository.ArticleRepository;
import com.fc8.projectboard.repository.CommentRepository;
import com.fc8.projectboard.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<CommentDto> searchComments(Long articleId) {
        List<Comment> comments = commentRepository.findByArticleId(articleId);
        return comments.stream().map(CommentDto::from).toList();
    }

    public void saveComment(CommentDto commentDto) {
        try {
            Article article = articleRepository.getReferenceById(commentDto.articleId());
            User user = userRepository.getReferenceById(commentDto.userDto().id());

            commentRepository.save(commentDto.toEntity(article, user));

        } catch (EntityNotFoundException e) {
            log.warn("댓글 저장 실패. 댓글 작성에 필요한 정보를 찾을 수 없습니다. - dto : {}", e.getLocalizedMessage());
        }
    }

    public void updateComment(CommentDto commentDto) {
        try {
            Comment comment = commentRepository.getReferenceById(commentDto.id());

            if (commentDto.content() != null) {
                comment.setContent(commentDto.content());
            }
        } catch (EntityNotFoundException e) {
            log.warn("댓글 업데이트 실패. 댓글을 찾을 수 없습니다. - dto : {}", commentDto);
        }
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
