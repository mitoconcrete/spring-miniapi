package com.api.post.dto;

import com.api.post.entity.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostResponseDto {
    private String title;
    private String writer;
    private String contents;
    private LocalDateTime modifiedAt;

    public PostResponseDto(Post post) {
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.writer = post.getWriter();
        this.modifiedAt = post.getModifiedAt();
    }
}
