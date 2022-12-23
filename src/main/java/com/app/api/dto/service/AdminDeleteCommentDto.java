package com.app.api.dto.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AdminDeleteCommentDto {
    private final Long postId;
    private final Long commentId;
}
