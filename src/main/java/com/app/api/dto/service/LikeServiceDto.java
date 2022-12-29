package com.app.api.dto.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LikeServiceDto {
    private final Long Id;
    private final String username;
}
