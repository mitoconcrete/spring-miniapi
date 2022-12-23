package com.app.api.service;

import com.app.api.dto.request.PostRequestDto;
import com.app.api.dto.response.PostResponseDto;
import com.app.api.dto.service.AuthorizedUserInfo;
import com.app.api.dto.service.CreatePostDto;
import com.app.api.dto.service.DeletePostDto;
import com.app.api.dto.service.UpdatePostDto;
import com.app.api.entity.Post;
import com.app.api.entity.User;
import com.app.api.entity.UserRefreshToken;
import com.app.api.exception.NotAuthorizedException;
import com.app.api.exception.NotFoundException;
import com.app.api.repository.PostRepository;
import com.app.api.repository.UserRepository;
import com.app.api.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService implements PostServiceInterface{
    private final PostRepository postRepository;

    @Override
    @Transactional(readOnly = true)
    public PostResponseDto getPost(Long id) {
        return new PostResponseDto(postRepository.findById(id).orElseThrow(
                () -> new NotFoundException("게시글이 존재하지 않습니다.")
        ));
    }

    @Transactional(readOnly = true)
    public Post _getPost(Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new NotFoundException("게시글이 존재하지 않습니다.")
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostResponseDto> getPosts() {
        return postRepository.findAllByOrderByModifiedAtDesc().stream().map(PostResponseDto::new).collect(Collectors.toList());
    }


    @Override
    @Transactional
    public PostResponseDto createPost(CreatePostDto createPostDto) {
        // create new post.
        Post post = new Post(createPostDto.getTitle(), createPostDto.getContents(), createPostDto.getWriter());
        // request new post to client.
        postRepository.save(post);
        return new PostResponseDto(post);
    }



    @Override
    @Transactional
    public PostResponseDto updatePost(UpdatePostDto updatePostDto) {
        // find post what authorized user write with match id.
        Post post = _getPost(updatePostDto.getId());

        if(!post.isWriterMatch(updatePostDto.getWriter())){
            throw new NotAuthorizedException("작성자만 삭제/수정할 수 있습니다.");
        }

        // update contents.
        post.updateContents(updatePostDto.getContents());
        postRepository.save(post);

        // response update post.
        return new PostResponseDto(post);
    }

    @Override
    @Transactional
    public void deletePost(DeletePostDto deletePostDto) {
        Post post = _getPost(deletePostDto.getId());

        if(!post.isWriterMatch(deletePostDto.getWriter())){
            throw new NotAuthorizedException("작성자만 삭제/수정할 수 있습니다.");
        }

        // remove post.
        postRepository.delete(post);
    }
}
