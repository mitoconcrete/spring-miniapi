package com.app.api.dto.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdatePostDto {
    private final Long id;
    private final String contents;
    private final String writer;
}
