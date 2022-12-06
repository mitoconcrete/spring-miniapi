package com.api.post.service;

import com.api.post.dto.PostRequestDto;
import com.api.post.entity.Post;
import com.api.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.CredentialException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public List<Post> getPosts() {
        return postRepository.findAllByOrderByModifiedAtDesc();
    }

    @Transactional
    public Post createPost(PostRequestDto postRequestDto) {
        Post post = new Post(postRequestDto);
        postRepository.save(post);
        return post;
    }

    @Transactional(readOnly = true)
    public Post getPost(Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시물이 없습니다.")
        );
    }

    @Transactional
    public Post updatePost(Long id, PostRequestDto postRequestDto) throws CredentialException {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당되는 게시물이 존재하지 않습니다."));

        if(!post.getPassword().equals(postRequestDto.getPassword())){
            throw new CredentialException("패스워드가 일치하지 않습니다.");
        }

        post.update(postRequestDto);
        postRepository.save(post);
        return post;
    }

    @Transactional
    public void deletePost(Long id, PostRequestDto postRequestDto) throws CredentialException{
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당되는 게시물이 존재하지 않습니다."));

        if(!post.getPassword().equals(postRequestDto.getPassword())){
            throw new CredentialException("패스워드가 일치하지 않습니다.");
        }

        postRepository.delete(post);
    }
}
