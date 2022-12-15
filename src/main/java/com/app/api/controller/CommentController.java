package com.app.api.controller;

import com.app.api.dto.request.CommentRequestDto;
import com.app.api.dto.response.CommentResponseDto;
import com.app.api.service.CommentService;
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

    @ApiOperation(value = "새 댓글 생성", notes = "지정된 게시글에 새로운 댓글을 생성합니다.")
    @PostMapping("/posts/{postId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponseDto createComment(@PathVariable Long postId, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) {
        return commentService.createComment(postId, commentRequestDto, request);
    }

    @ApiOperation(value = "지정된 댓글 수정", notes = "지정된 게시글의 지정된 댓글을 수정합니다.<br>해당 댓글이 자신이 작성한 댓글이 아니라면 수정은 불가능합니다.<br>하지만, 관리자는 모든 댓글을 수정할 수 있습니다.")
    @PutMapping("/posts/{postId}/comments/{commentId}")
    public CommentResponseDto updateComment(@PathVariable Long postId, @PathVariable Long commentId,@RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) {
        return commentService.updateComment(postId, commentId, commentRequestDto, request);
    }


    @ApiOperation(value = "지정된 댓글 삭제", notes = "지정된 게시글의 지정된 댓글을 삭제합니다.<br>해당 댓글이 자신이 작성한 댓글이 아니라면 삭제는 불가능합니다.<br>하지만, 관리자는 모든 댓글을 삭제할 수 있습니다.")
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public String deleteComment(@PathVariable Long postId,@PathVariable Long commentId ,HttpServletRequest request) {
        commentService.deleteComment(postId, commentId, request);
        return "댓글 삭제에 성공했습니다.";
    }
}
