package com.app.api.controller;

import com.app.api.dto.CommentRequestDto;
import com.app.api.dto.CommentResponseDto;
import com.app.api.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    @PostMapping("/posts/{postId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponseDto createComment(@PathVariable Long postId, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) {
        return commentService.createComment(postId, commentRequestDto, request);
    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    public CommentResponseDto updateComment(@PathVariable Long postId, @PathVariable Long commentId,@RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) {
        return commentService.updateComment(postId, commentId, commentRequestDto, request);
    }


    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public String deleteComment(@PathVariable Long postId,@PathVariable Long commentId ,HttpServletRequest request) {
        commentService.deleteComment(postId, commentId, request);
        return "comment remove complete.";
    }
}
