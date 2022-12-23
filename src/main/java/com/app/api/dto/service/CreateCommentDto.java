package com.app.api.dto.service;

import com.app.api.dto.response.PostResponseDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateCommentDto {
    private final Long postId;
    private final String contents;
    private final String writer;
    private final PostResponseDto postDto;
}
