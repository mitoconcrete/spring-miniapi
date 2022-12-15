package com.app.api.service;

import com.app.api.dto.request.PostRequestDto;
import com.app.api.dto.response.PostResponseDto;
import com.app.api.entity.Post;
import com.app.api.entity.User;
import com.app.api.repository.PostRepository;
import com.app.api.repository.UserRepository;
import com.app.api.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService implements PostServiceInterface{
    private final PostRepository postRepository;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<PostResponseDto> getPosts() {
        return postRepository.findAllByOrderByModifiedAtDesc().stream().map(PostResponseDto::new).collect(Collectors.toList());
    }

    @Override
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

    @Override
    @Transactional(readOnly = true)
    public PostResponseDto getPost(Long id) {
        return new PostResponseDto(postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        ));
    }

    @Override
    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto postRequestDto, HttpServletRequest request) {
        // get authorized user.
        User user = getValidUserFromRequestHeader(request);

        // find post what authorized user write with match id.
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        if(!user.isAdmin() && !post.isAuthorIdMatchUserId(user.getId())){
            throw new IllegalArgumentException("작성자만 삭제/수정할 수 있습니다.");
        }

        // update contents.
        post.updateContents(postRequestDto.getContents());
        postRepository.save(post);

        // response update post.
        return new PostResponseDto(post);
    }

    @Override
    @Transactional
    public void deletePost(Long id, HttpServletRequest request) {
        // get authorized user
        User user = getValidUserFromRequestHeader(request);

        // find post what authorized user write with match id.
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        if(!user.isAdmin() && !post.isAuthorIdMatchUserId(user.getId())){
            throw new IllegalArgumentException("작성자만 삭제/수정할 수 있습니다.");
        }

        // remove post.
        postRepository.delete(post);
    }

    // check user authorization and get user instance.
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
