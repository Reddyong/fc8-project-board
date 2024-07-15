package com.fc8.projectboard.service;

import com.fc8.projectboard.domain.Comment;
import com.fc8.projectboard.dto.CommentDto;
import com.fc8.projectboard.repository.ArticleRepository;
import com.fc8.projectboard.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    @Transactional(readOnly = true)
    public List<CommentDto> searchComments(Long articleId) {
        return List.of();
    }

    public void saveComment(CommentDto commentDto) {

    }

    public void updateComment(CommentDto commentDto) {

    }

    public void deleteComment(Long commentId) {

    }
}
