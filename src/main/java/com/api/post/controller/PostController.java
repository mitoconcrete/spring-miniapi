package com.api.post.controller;

import com.api.post.dto.PostRequestDto;
import com.api.post.dto.PostResponseDto;
import com.api.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    @GetMapping("/posts")
    public List<PostResponseDto> getPosts(){
        return postService.getPosts();
    }

    @PostMapping("/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponseDto createPost(@RequestBody PostRequestDto postRequestDto, HttpServletRequest request){
        return postService.createPost(postRequestDto, request);
    }

    @GetMapping("/posts/{id}")
    public PostResponseDto getPost(@PathVariable Long id){
        return postService.getPost(id);
    }

    @PutMapping("/posts/{id}")
    public PostResponseDto updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto, HttpServletRequest request){
        return postService.updatePost(id, postRequestDto, request);
    }

    @DeleteMapping("/posts/{id}")
    public String deletePost(@PathVariable Long id, HttpServletRequest request){
        postService.deletePost(id, request);
        return "delete post complete.";
    }
}
