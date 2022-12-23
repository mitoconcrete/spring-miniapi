package com.app.api.service;

import com.app.api.dto.request.CommentRequestDto;
import com.app.api.dto.response.CommentResponseDto;
import com.app.api.dto.service.*;

import javax.servlet.http.HttpServletRequest;

public interface CommentServiceInterface {
    CommentResponseDto createComment(CreateCommentDto createCommentDto);
    CommentResponseDto updateComment(UpdateCommentDto updateCommentDto);
    void deleteComment(DeleteCommentDto deleteCommentDto);
    CommentResponseDto adminUpdateComment(AdminUpdateCommentDto updateCommentDto);
    void adminDeleteComment(AdminDeleteCommentDto deleteCommentDto);
}
