package com.api.post.service;

import com.api.post.dto.CommentRequestDto;
import com.api.post.dto.CommentResponseDto;

import javax.servlet.http.HttpServletRequest;

public interface CommentServiceInterface {
    CommentResponseDto createComment(Long postId, CommentRequestDto commentRequestDto, HttpServletRequest request);
    CommentResponseDto updateComment(Long postId, CommentRequestDto commentRequestDto, HttpServletRequest request);
    void deleteComment(Long postId, HttpServletRequest request);
}
