package com.api.post.service;

import com.api.post.dto.PostRequestDto;
import com.api.post.entity.Post;
import com.api.post.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostService {
    PostRepository postRepository;

    @Transactional(readOnly = true)
    public List<Post> getPosts(){
        return postRepository.findAllByOrderByModifiedAtDesc();
    }

    @Transactional
    public Post createPost(PostRequestDto postRequestDto){
        Post post = new Post(postRequestDto);
        postRepository.save(post);
        return post;
    }

    @Transactional(readOnly = true)
    public Post getPostById(Long id){
        return postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시물이 없습니다.")
        );
    }

    @Transactional
    public Post updatePost(Long id, PostRequestDto postRequestDto){
        return postRepository.findByIdAndPassword(id, postRequestDto.getPassword()).orElseThrow(
                () -> new IllegalArgumentException("해당되는 게시물이 존재하지 않습니다."));
    }
}
