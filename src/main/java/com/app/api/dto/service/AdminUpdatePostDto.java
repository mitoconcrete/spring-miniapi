package com.app.api.dto.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AdminUpdatePostDto {
    private final Long id;
    private final String contents;
}
