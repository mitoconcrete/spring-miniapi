package com.app.api.dto.response;

import com.app.api.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private final Long postId;
    private final String username;
    private final String contents;
    private final LocalDateTime modifiedAt;

    public CommentResponseDto(Comment comment) {
        this.postId = comment.getPost().getId();
        this.username = comment.getUser().getUsername();
        this.contents = comment.getContents();
        this.modifiedAt = comment.getModifiedAt();
    }
}
