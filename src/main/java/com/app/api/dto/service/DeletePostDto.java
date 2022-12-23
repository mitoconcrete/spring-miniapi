package com.app.api.dto.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DeletePostDto {
    private final Long id;
    private final AuthorizedUserInfo userInfo;
}
