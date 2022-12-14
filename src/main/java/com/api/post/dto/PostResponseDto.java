package com.api.post.dto;

import com.api.post.entity.Comment;
import com.api.post.entity.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostResponseDto {
    private final String title;
    private final String contents;
    private final LocalDateTime modifiedAt;

    private final String username;

    private final List<Comment> comments;

    public PostResponseDto(Post post) {
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.modifiedAt = post.getModifiedAt();
        this.username = post.getUser().getUsername();
        this.comments = post.getComments();
    }
}
