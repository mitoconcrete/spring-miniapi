package com.app.api.dto;

import com.app.api.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostResponseDto {
    private final String title;
    private final String contents;
    private final LocalDateTime modifiedAt;

    private final String username;

    private final List<CommentResponseDto> comments;

    public PostResponseDto(Post post) {
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.modifiedAt = post.getModifiedAt();
        this.username = post.getUser().getUsername();
        this.comments = post.getComments().stream().map(CommentResponseDto::new).collect(Collectors.toList());
    }
}