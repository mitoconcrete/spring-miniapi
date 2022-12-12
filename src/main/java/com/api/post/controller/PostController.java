package com.api.post.controller;

import com.api.post.dto.PostRequestDto;
import com.api.post.dto.PostResponseDto;
import com.api.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    @GetMapping("/posts")
    public ResponseEntity<Object> getPosts(){
        return
                ResponseEntity.ok(postService.getPosts());
    }

    @PostMapping("/posts")
    public ResponseEntity<Object> createPost(@RequestBody PostRequestDto postRequestDto){
        return ResponseEntity.ok(new PostResponseDto(postService.createPost(postRequestDto)));
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<Object> getPost(@PathVariable Long id){
        try{
        return ResponseEntity.ok(new PostResponseDto(postService.getPost(id)));
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<Object> updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto){
        try{
            return ResponseEntity.ok(new PostResponseDto(postService.updatePost(id, postRequestDto)));
        }catch (IllegalArgumentException | CredentialException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Object> deletePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto){
        try{
            postService.deletePost(id, postRequestDto);
            return ResponseEntity.ok("delete complete");
        }catch (IllegalArgumentException | CredentialException e){
            return ResponseEntity.badRequest().body("delete fail : " + e.getMessage());
        }
    }
}
