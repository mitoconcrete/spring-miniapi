package com.api.post.service;

import com.api.post.dto.PostRequestDto;
import com.api.post.dto.PostResponseDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface PostServiceInterface {
    List<PostResponseDto> getPosts();
    PostResponseDto createPost(PostRequestDto postRequestDto, HttpServletRequest request);
    PostResponseDto getPost(Long id, PostRequestDto postRequestDto);
    PostResponseDto updatePost(Long id, PostRequestDto postRequestDto, HttpServletRequest request);
    void deletePost(Long id, HttpServletRequest request);
}
