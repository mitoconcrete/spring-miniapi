package com.api.post.controller;

import com.api.post.dto.CommentRequestDto;
import com.api.post.dto.CommentResponseDto;
import com.api.post.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    @PostMapping("/comments/{postId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponseDto createComment(@PathVariable Long postId, CommentRequestDto commentRequestDto, HttpServletRequest request) {
        return commentService.createComment(postId, commentRequestDto, request);
    }

    @PutMapping("/comments/{postId}")
    public CommentResponseDto updateComment(@PathVariable Long postId, CommentRequestDto commentRequestDto, HttpServletRequest request) {
        return commentService.updateComment(postId, commentRequestDto, request);
    }


    @DeleteMapping("/comments/{postId}")
    public String deleteComment(@PathVariable Long postId, HttpServletRequest request) {
        commentService.deleteComment(postId, request);
        return "comment remove complete.";
    }
}
