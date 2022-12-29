package com.app.api.dto.response;

import com.app.api.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostResponseDto {
    private final Long id;
    private final String title;
    private final String contents;
    private final LocalDateTime modifiedAt;
    private final String writer;
    private final List<CommentResponseDto> comments;
    private final Long likeCounts;

    public PostResponseDto(Post post, Long likeCounts) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.modifiedAt = post.getModifiedAt();
        this.writer = post.getWriter();
        this.likeCounts = post.getLikes().stream().filter(like -> like.getIsRemoved().equals(false)).count();
        this.comments = post.getComments().stream().map(CommentResponseDto::new).collect(Collectors.toList());
    }

    public Post toEntity(){
        return new Post(this.id, this.title, this.contents, this.writer);
    }
}
