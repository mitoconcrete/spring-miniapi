package com.api.post.service;

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
}
