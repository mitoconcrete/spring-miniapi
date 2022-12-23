package com.app.api.service;

import com.app.api.dto.request.CommentRequestDto;
import com.app.api.dto.response.CommentResponseDto;
import com.app.api.dto.service.CreateCommentDto;
import com.app.api.dto.service.DeleteCommentDto;
import com.app.api.dto.service.UpdateCommentDto;
import com.app.api.entity.Comment;
import com.app.api.entity.Post;
import com.app.api.entity.User;
import com.app.api.entity.UserRefreshToken;
import com.app.api.exception.NotAuthorizedException;
import com.app.api.exception.NotFoundException;
import com.app.api.repository.CommentRepository;
import com.app.api.repository.PostRepository;
import com.app.api.repository.UserRepository;
import com.app.api.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class CommentService implements CommentServiceInterface{
    private final CommentRepository commentRepository;

    @Override
    @Transactional
    public CommentResponseDto createComment(CreateCommentDto createCommentDto) {
        Post post = createCommentDto.getPostDto().toEntity();
        // create new comment and attach to post.
        Comment comment = new Comment(post, createCommentDto.getWriter(), createCommentDto.getContents());
        // save comment.
        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }

    @Override
    @Transactional
    public CommentResponseDto updateComment(UpdateCommentDto updateCommentDto) {
        // get comment in db.
        Comment comment = commentRepository.findByIdAndPost_Id(updateCommentDto.getCommentId(), updateCommentDto.getPostId()).orElseThrow(
                () -> new NotFoundException("번호에 해당되는 댓글을 찾을 수 없습니다.")
        );
        if(!comment.isWriterMatch(updateCommentDto.getWriter())){
            throw new NotAuthorizedException("작성자만 삭제/수정할 수 있습니다.");
        }
        // update comment's contents.
        comment.updateContents(updateCommentDto.getContents());
        // save
        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }

    @Override
    @Transactional
    public void deleteComment(DeleteCommentDto deleteCommentDto) {
        // get comment in db..
        Comment comment = commentRepository.findByIdAndPost_Id(deleteCommentDto.getCommentId(), deleteCommentDto.getPostId()).orElseThrow(
                () -> new NotFoundException("번호에 해당되는 댓글을 찾을 수 없습니다.")
        );
        if(!comment.isWriterMatch(deleteCommentDto.getWriter())){
            throw new NotAuthorizedException("작성자만 삭제/수정할 수 있습니다.");
        }
        // delete comment.
        commentRepository.delete(comment);
    }
}
