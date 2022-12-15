package com.app.api.service;

import com.app.api.dto.CommentRequestDto;
import com.app.api.dto.CommentResponseDto;

import javax.servlet.http.HttpServletRequest;

public interface CommentServiceInterface {
    CommentResponseDto createComment(Long postId, CommentRequestDto commentRequestDto, HttpServletRequest request);
    CommentResponseDto updateComment(Long postId, Long commentId, CommentRequestDto commentRequestDto, HttpServletRequest request);
    void deleteComment(Long postId, Long commentId, HttpServletRequest request);
}
