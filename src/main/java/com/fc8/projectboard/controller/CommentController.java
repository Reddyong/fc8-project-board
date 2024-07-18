package com.fc8.projectboard.controller;

import com.fc8.projectboard.dto.CommentDto;
import com.fc8.projectboard.dto.UserDto;
import com.fc8.projectboard.dto.request.CommentRequest;
import com.fc8.projectboard.service.CommentService;
import lombok.RequiredArgsConstructor;
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
    public String postNewComment(CommentRequest commentRequest) {
        // TODO : 인증 기능 넣어주기
        commentService.saveComment(commentRequest.toDto(UserDto.of(1L, "reddyong", "password", "reddyong@email.com", "reddyong", "memo")));

        return "redirect:/articles/" + commentRequest.articleId();
    }

    @PostMapping(path = "/{commentId}/delete")
    public String deleteComment(@PathVariable Long commentId, Long articleId) {

        commentService.deleteComment(commentId);

        return "redirect:/articles/" + articleId;
    }
}
