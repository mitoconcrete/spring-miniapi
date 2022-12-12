package com.api.post.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class PostRequestDto {
    private String title;
    private String writer;
    private String contents;
    private String password;
}
