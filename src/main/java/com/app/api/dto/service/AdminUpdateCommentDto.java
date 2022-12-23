package com.app.api.dto.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AdminUpdateCommentDto {
    private final Long postId;
    private final Long commentId;
    private final String contents;
}
