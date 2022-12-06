package com.api.post.controller;

import com.api.post.dto.PostRequestDto;
import com.api.post.dto.PostResponseDto;
import com.api.post.entity.Post;
import com.api.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.CredentialException;
import java.net.http.HttpResponse;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    @GetMapping("/api/posts")
    public List<Post> getPosts(){
        return postService.getPosts();
    }

    @PostMapping("/api/posts")
    public PostResponseDto createPost(@RequestBody PostRequestDto postRequestDto){
        return new PostResponseDto(postService.createPost(postRequestDto));
    }

    @GetMapping("/api/posts/{id}")
    public ResponseEntity getPost(@PathVariable Long id){
        try{
        return new ResponseEntity(new PostResponseDto(postService.getPost(id)), HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/api/posts/{id}")
    public ResponseEntity updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto){
        try{
            return new ResponseEntity(new PostResponseDto(postService.updatePost(id, postRequestDto)), HttpStatus.OK);
        }catch (IllegalArgumentException | CredentialException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/api/posts/{id}")
    public HttpStatus deletePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto){
        try{
            postService.deletePost(id, postRequestDto);
            return HttpStatus.OK;
        }catch (IllegalArgumentException | CredentialException e){
            return HttpStatus.BAD_REQUEST;
        }
    }
}
