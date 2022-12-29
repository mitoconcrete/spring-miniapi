package com.app.api.controller;

import com.app.api.dto.service.LikeServiceDto;
import com.app.api.security.UserDetailsImpl;
import com.app.api.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class PostLikeController {
    PostLikeService postLikeService;

    @PostMapping("/like/posts/{postId}")
    public void toggleCommentLike(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        LikeServiceDto likeServiceDto = new LikeServiceDto(postId, userDetails.getUsername());
        postLikeService.toggleLike(likeServiceDto);
    }
}
