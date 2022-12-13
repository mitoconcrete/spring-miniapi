package com.api.post.service;

import com.api.post.dto.PostRequestDto;
import com.api.post.dto.PostResponseDto;
import com.api.post.entity.Post;
import com.api.post.entity.User;
import com.api.post.repository.PostRepository;
import com.api.post.repository.UserRepository;
import com.api.post.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<PostResponseDto> getPosts() {
        return postRepository.findAllByOrderByModifiedAtDesc().stream().map(PostResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public PostResponseDto createPost(PostRequestDto postRequestDto, HttpServletRequest request) {
        // get validate user.
        User user = getValidUserFromRequestHeader(request);

        // create new post.
        Post post = new Post(postRequestDto.getTitle(), postRequestDto.getContents(), user);

        // request new post to client.
        postRepository.save(post);
        return new PostResponseDto(post);
    }

    @Transactional(readOnly = true)
    public PostResponseDto getPost(Long id) {
        return new PostResponseDto(postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시물이 없습니다.")
        ));
    }

    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto postRequestDto){
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당되는 게시물이 존재하지 않습니다."));

        if(post.isPasswordValid(postRequestDto.getPassword())){
            throw new IllegalArgumentException("패스워드가 일치하지 않습니다.");
        }

        post.updateContents(postRequestDto.getContents());
        postRepository.save(post);

        return new PostResponseDto(post);
    }

    @Transactional
    public void deletePost(Long id, PostRequestDto postRequestDto){
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당되는 게시물이 존재하지 않습니다."));

        // TODO: TOKEN 검증

        postRepository.delete(post);
    }

    // check user authorization and get user instance.
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
