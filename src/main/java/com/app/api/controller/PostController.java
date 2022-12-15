package com.app.api.controller;

import com.app.api.dto.request.PostRequestDto;
import com.app.api.dto.response.PostResponseDto;
import com.app.api.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(tags = "게시글")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @ApiOperation(value = "모든 게시글 조회", notes = "모든 게시글을 조회 할 수 있습니다.<br>게시글에 포함 된 댓글들도 조회합니다.")
    @GetMapping("/posts")
    public List<PostResponseDto> getPosts(){
        return postService.getPosts();
    }

    @ApiOperation(value = "새 게시글 생성", notes = "새로운 게시글을 생성합니다.")
    @PostMapping("/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponseDto createPost(@RequestBody PostRequestDto postRequestDto, HttpServletRequest request){
        return postService.createPost(postRequestDto, request);
    }

    @ApiOperation(value = "지정된 게시글 조회", notes = "지정된 게시글을 조회 할 수 있습니다.<br>게시글에 포함 된 댓글들도 조회합니다.")
    @GetMapping("/posts/{id}")
    public PostResponseDto getPost(@PathVariable Long id){
        return postService.getPost(id);
    }

    @ApiOperation(value = "지정된 게시글 수정", notes = "지정된 게시글의 내용을 수정합니다.<br>해당 게시물이 자신이 작성한 댓글이 아니라면 수정은 불가능합니다.<br>하지만, 관리자는 모든 게시물을 수정할 수 있습니다.")
    @PutMapping("/posts/{id}")
    public PostResponseDto updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto, HttpServletRequest request){
        return postService.updatePost(id, postRequestDto, request);
    }

    @ApiOperation(value = "지정된 게시글 삭제", notes = "지정된 게시글의 내용을 삭제합니다.<br>해당 게시물이 자신이 작성한 댓글이 아니라면 삭제는 불가능합니다.<br>하지만, 관리자는 모든 게시물을 삭제할 수 있습니다.")
    @DeleteMapping("/posts/{id}")
    public String deletePost(@PathVariable Long id, HttpServletRequest request){
        postService.deletePost(id, request);
        return "게시글 삭제에 성공했습니다.";
    }
}
