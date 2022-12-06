package com.api.post.controller;

import com.api.post.entity.Post;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PostController {
    PostService postService;
    @GetMapping("/api/posts")
    public List<Post> getPosts(){
        return
    }
}
