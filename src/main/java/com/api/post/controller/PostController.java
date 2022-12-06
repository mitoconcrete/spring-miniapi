package com.api.post.controller;

import com.api.post.dto.PostRequestDto;
import com.api.post.dto.PostResponseDto;
import com.api.post.entity.Post;
import com.api.post.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {
    PostService postService;
    @GetMapping("/api/posts")
    public List<Post> getPosts(){
        return postService.getPosts();
    }

    @PostMapping("/api/posts")
    public PostResponseDto createPost(@RequestBody PostRequestDto postRequestDto){
        return new PostResponseDto(postService.createPost(postRequestDto));
    }

    @GetMapping("/api/posts/{id}")
    public PostResponseDto getPost(@PathVariable Long id){
        return new PostResponseDto(postService.getPost(id));
    }

    @PutMapping("/api/posts/{id}")
    public PostResponseDto updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto){
        return new PostResponseDto(postService.updatePost(id, postRequestDto));
    }

    @DeleteMapping("/api/post/{id}")
    public HttpStatus deletePost(@PathVariable Long id){
        postService.deletePost(id);
        return HttpStatus.OK;
    }
}
