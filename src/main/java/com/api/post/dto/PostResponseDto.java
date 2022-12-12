package com.api.post.dto;

import com.api.post.entity.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {
    private final String title;
    private final String writer;
    private final String contents;
    private final LocalDateTime modifiedAt;

    public PostResponseDto(Post post) {
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.writer = post.getWriter();
        this.modifiedAt = post.getModifiedAt();
    }
}
