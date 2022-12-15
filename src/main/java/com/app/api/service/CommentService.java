package com.app.api.service;

import com.app.api.dto.request.CommentRequestDto;
import com.app.api.dto.response.CommentResponseDto;
import com.app.api.entity.Comment;
import com.app.api.entity.Post;
import com.app.api.entity.User;
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
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    @Override
    @Transactional
    public CommentResponseDto createComment(Long postId, CommentRequestDto commentRequestDto, HttpServletRequest request) {
        // get authorized user.
        User user = getValidUserFromRequestHeader(request);

        // get user's post.
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );

        // create new comment and attach to post.
        Comment comment = new Comment(post, user, commentRequestDto.getContents());

        // save comment.
        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }

    @Override
    @Transactional
    public CommentResponseDto updateComment(Long postId, Long commentId, CommentRequestDto commentRequestDto, HttpServletRequest request) {
        // get authorized user.
        User user = getValidUserFromRequestHeader(request);

        // get post in db.
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );

        // get comment in db.
        Comment comment = commentRepository.findByIdAndPost_Id(commentId, post.getId()).orElseThrow(
                () -> new IllegalArgumentException("번호에 해당되는 댓글을 찾을 수 없습니다.")
        );

        if(!user.isAdmin() && !comment.isAuthorIdMatchUserId(user.getId())){
            throw new IllegalArgumentException("작성자만 삭제/수정할 수 있습니다.");
        }

        // update comment's contents.
        comment.updateContents(commentRequestDto.getContents());

        // save
        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }

    @Override
    @Transactional
    public void deleteComment(Long postId, Long commentId, HttpServletRequest request) {
        // get authorized user.
        User user = getValidUserFromRequestHeader(request);

        // get post in db.
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );

        // get comment in db..
        Comment comment = commentRepository.findByIdAndPost_Id(commentId, post.getId()).orElseThrow(
                () -> new IllegalArgumentException("번호에 해당되는 댓글을 찾을 수 없습니다.")
        );

        if(!user.isAdmin() && !comment.isAuthorIdMatchUserId(user.getId())){
            throw new IllegalArgumentException("작성자만 삭제/수정할 수 있습니다.");
        }

        // delete comment.
        commentRepository.delete(comment);
    }

    private User getValidUserFromRequestHeader(HttpServletRequest request){
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        if(jwtUtil.validateToken(token)){
            claims = jwtUtil.getUserInfoFromToken(token);
        }else{
            throw new IllegalArgumentException("토큰이 유효하지 않습니다.");
        }

        return userRepository.findByUsername(claims.getSubject()).orElseThrow(
                () -> new IllegalArgumentException("유저가 존재하지 않습니다.")
        );
    }
}
