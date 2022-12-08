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
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    @GetMapping("/api/posts")
    public ResponseEntity<Object> getPosts(){
        return
                ResponseEntity.ok(postService.getPosts());
    }

    @PostMapping("/api/posts")
    public ResponseEntity<Object> createPost(@RequestBody PostRequestDto postRequestDto){
        return ResponseEntity.ok(new PostResponseDto(postService.createPost(postRequestDto)));
    }

    @GetMapping("/api/posts/{id}")
    public ResponseEntity<Object> getPost(@PathVariable Long id){
        try{
        return ResponseEntity.ok(new PostResponseDto(postService.getPost(id)));
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/api/posts/{id}")
    public ResponseEntity<Object> updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto){
        try{
            return ResponseEntity.ok(new PostResponseDto(postService.updatePost(id, postRequestDto)));
        }catch (IllegalArgumentException | CredentialException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/api/posts/{id}")
    public ResponseEntity<Object> deletePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto){
        try{
            postService.deletePost(id, postRequestDto);
            return ResponseEntity.ok("delete complete");
        }catch (IllegalArgumentException | CredentialException e){
            return ResponseEntity.badRequest().body("delete fail : " + e.getMessage());
        }
    }
}
