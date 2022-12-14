package com.api.post.service;

import com.api.post.dto.CommentRequestDto;
import com.api.post.dto.CommentResponseDto;
import com.api.post.entity.Comment;
import com.api.post.entity.Post;
import com.api.post.entity.User;
import com.api.post.repository.CommentRepository;
import com.api.post.repository.PostRepository;
import com.api.post.repository.UserRepository;
import com.api.post.utils.JwtUtil;
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
        Post post = postRepository.findByIdAndUser(postId, user).orElseThrow(
                () -> new IllegalArgumentException("not exist post.")
        );

        // create new comment and attach to post.
        Comment comment = new Comment(commentRequestDto.getContents(), post);

        // save comment.
        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }

    @Override
    @Transactional
    public CommentResponseDto updateComment(Long postId, CommentRequestDto commentRequestDto, HttpServletRequest request) {
        return null;
    }

    @Override
    @Transactional
    public void deleteComment(Long postId, HttpServletRequest request) {

    }

    public User getValidUserFromRequestHeader(HttpServletRequest request){
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        if(jwtUtil.validateToken(token)){
            claims = jwtUtil.getUserInfoFromToken(token);
        }else{
            throw new IllegalArgumentException("token is invalid");
        }

        return userRepository.findByUsername(claims.getSubject()).orElseThrow(
                () -> new IllegalArgumentException("not exist user.")
        );
    }
}
