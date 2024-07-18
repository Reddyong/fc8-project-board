package com.fc8.projectboard.controller;

import com.fc8.projectboard.dto.CommentDto;
import com.fc8.projectboard.dto.UserDto;
import com.fc8.projectboard.dto.request.CommentRequest;
import com.fc8.projectboard.dto.security.BoardPrincipal;
import com.fc8.projectboard.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping(path = "/comments")
@Controller
public class CommentController {

    private final CommentService commentService;

    @PostMapping(path = "/new")
    public String postNewComment(
            @AuthenticationPrincipal BoardPrincipal boardPrincipal,
            CommentRequest commentRequest
    ) {
        commentService.saveComment(commentRequest.toDto(boardPrincipal.toDto()));

        return "redirect:/articles/" + commentRequest.articleId();
    }

    @PostMapping(path = "/{commentId}/delete")
    public String deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal BoardPrincipal boardPrincipal,
            Long articleId) {

        String userId = boardPrincipal.getUsername();
        commentService.deleteComment(commentId, userId);

        return "redirect:/articles/" + articleId;
    }
}
