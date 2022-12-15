package com.app.api.service;

import com.app.api.dto.request.PostRequestDto;
import com.app.api.dto.response.PostResponseDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface PostServiceInterface {
    List<PostResponseDto> getPosts();
    PostResponseDto createPost(PostRequestDto postRequestDto, HttpServletRequest request);
    PostResponseDto getPost(Long id);
    PostResponseDto updatePost(Long id, PostRequestDto postRequestDto, HttpServletRequest request);
    void deletePost(Long id, HttpServletRequest request);
}
