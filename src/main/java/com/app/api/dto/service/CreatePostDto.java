package com.app.api.dto.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreatePostDto {
    private final String title;
    private final String contents;
    private final String writer;
}
