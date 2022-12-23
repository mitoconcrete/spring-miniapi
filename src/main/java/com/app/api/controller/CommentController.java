package com.app.api.controller;

import com.app.api.dto.request.CommentRequestDto;
import com.app.api.dto.response.CommentResponseDto;
import com.app.api.dto.response.PostResponseDto;
import com.app.api.dto.service.*;
import com.app.api.exception.NotAuthorizedException;
import com.app.api.service.AuthorizationService;
import com.app.api.service.CommentService;
import com.app.api.service.PostService;
import com.app.api.utils.JwtUtil;
import com.app.api.utils.TokenType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "댓글")
@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final AuthorizationService authorizationService;
    private final PostService postService;
    private final JwtUtil jwtUtil;

    @ApiOperation(value = "새 댓글 생성", notes = "지정된 게시글에 새로운 댓글을 생성합니다.")
    @PostMapping("/posts/{postId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponseDto createComment(@PathVariable Long postId, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        if(token == null){
            throw new NotAuthorizedException("토큰이 존재하지 않습니다.");
        }
        AuthorizedUserInfo userInfo = authorizationService.getAuthorizedUserInfo(token);
        if(userInfo.getTokenType().equals(TokenType.REFRESH)){
            throw new NotAuthorizedException("유효하지 않은 토큰입니다.");
        }
        PostResponseDto postResponseDto = postService.getPost(postId);
        CreateCommentDto createCommentDto = new CreateCommentDto(postId, commentRequestDto.getContents(), userInfo.getUsername(), postResponseDto);
        return commentService.createComment(createCommentDto);
    }

    @ApiOperation(value = "지정된 댓글 수정", notes = "지정된 게시글의 지정된 댓글을 수정합니다.<br>해당 댓글이 자신이 작성한 댓글이 아니라면 수정은 불가능합니다.<br>하지만, 관리자는 모든 댓글을 수정할 수 있습니다.")
    @PutMapping("/posts/{postId}/comments/{commentId}")
    public CommentResponseDto updateComment(@PathVariable Long postId, @PathVariable Long commentId,@RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        if(token == null){
            throw new NotAuthorizedException("토큰이 존재하지 않습니다.");
        }
        AuthorizedUserInfo userInfo = authorizationService.getAuthorizedUserInfo(token);
        if(userInfo.getTokenType().equals(TokenType.REFRESH)){
            throw new NotAuthorizedException("유효하지 않은 토큰입니다.");
        }
        UpdateCommentDto updateCommentDto = new UpdateCommentDto(postId, commentId, commentRequestDto.getContents(), userInfo.getUsername());
        return commentService.updateComment(updateCommentDto);
    }


    @ApiOperation(value = "지정된 댓글 삭제", notes = "지정된 게시글의 지정된 댓글을 삭제합니다.<br>해당 댓글이 자신이 작성한 댓글이 아니라면 삭제는 불가능합니다.<br>하지만, 관리자는 모든 댓글을 삭제할 수 있습니다.")
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public String deleteComment(@PathVariable Long postId,@PathVariable Long commentId ,HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        if(token == null){
            throw new NotAuthorizedException("토큰이 존재하지 않습니다.");
        }
        AuthorizedUserInfo userInfo = authorizationService.getAuthorizedUserInfo(token);
        if(userInfo.getTokenType().equals(TokenType.REFRESH)){
            throw new NotAuthorizedException("유효하지 않은 토큰입니다.");
        }
        DeleteCommentDto deleteCommentDto = new DeleteCommentDto(postId, commentId, userInfo.getUsername());
        commentService.deleteComment(deleteCommentDto);
        return "댓글 삭제에 성공했습니다.";
    }

    @PutMapping("/admin/posts/{postId}/comments/{commentId}")
    public CommentResponseDto adminUpdateComment(@PathVariable Long postId, @PathVariable Long commentId,@RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        if(token == null){
            throw new NotAuthorizedException("토큰이 존재하지 않습니다.");
        }
        AuthorizedUserInfo userInfo = authorizationService.getAuthorizedUserInfo(token);
        if(userInfo.getTokenType().equals(TokenType.REFRESH)){
            throw new NotAuthorizedException("유효하지 않은 토큰입니다.");
        }
        AdminUpdateCommentDto updateCommentDto = new AdminUpdateCommentDto(postId, commentId, commentRequestDto.getContents());
        return commentService.adminUpdateComment(updateCommentDto);
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public String adminDeleteComment(@PathVariable Long postId,@PathVariable Long commentId ,HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        if(token == null){
            throw new NotAuthorizedException("토큰이 존재하지 않습니다.");
        }
        AuthorizedUserInfo userInfo = authorizationService.getAuthorizedUserInfo(token);
        if(userInfo.getTokenType().equals(TokenType.REFRESH)){
            throw new NotAuthorizedException("유효하지 않은 토큰입니다.");
        }
        AdminDeleteCommentDto deleteCommentDto = new AdminDeleteCommentDto(postId, commentId);
        commentService.adminDeleteComment(deleteCommentDto);
        return "댓글 삭제에 성공했습니다.";
    }

}
