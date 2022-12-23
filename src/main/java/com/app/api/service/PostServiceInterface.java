package com.app.api.service;

import com.app.api.dto.request.PostRequestDto;
import com.app.api.dto.response.PostResponseDto;
import com.app.api.dto.service.CreatePostDto;
import com.app.api.dto.service.DeletePostDto;
import com.app.api.dto.service.UpdatePostDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface PostServiceInterface {
    PostResponseDto getPost(Long id);
    List<PostResponseDto> getPosts();
    PostResponseDto createPost(CreatePostDto createPostDto);
    PostResponseDto updatePost(UpdatePostDto updatePostDto);
    void deletePost(DeletePostDto deletePostDto);
}
