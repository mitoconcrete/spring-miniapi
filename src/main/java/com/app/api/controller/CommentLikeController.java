package com.app.api.controller;

import com.app.api.dto.service.LikeServiceDto;
import com.app.api.security.UserDetailsImpl;
import com.app.api.service.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class CommentLikeController {
    CommentLikeService commentLikeService;

    @PostMapping("/like/comments/{commentId}")
    public void toggleCommentLike(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        LikeServiceDto likeServiceDto = new LikeServiceDto(commentId, userDetails.getUsername());
        commentLikeService.toggleLike(likeServiceDto);
    }
}
