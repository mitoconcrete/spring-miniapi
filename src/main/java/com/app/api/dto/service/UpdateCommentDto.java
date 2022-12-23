package com.app.api.dto.service;

import com.app.api.dto.response.PostResponseDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdateCommentDto {
    private final Long postId;
    private final Long commentId;
    private final String contents;
    private final String writer;
}
