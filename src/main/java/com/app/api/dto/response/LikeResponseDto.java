package com.app.api.dto.response;

import com.app.api.entity.BaseLike;
import lombok.Getter;

@Getter
public class LikeResponseDto {
    private final String username;
    private final Boolean isRemoved;

    public LikeResponseDto(BaseLike like){
        this.username = like.getUsername();
        this.isRemoved = like.getIsRemoved();
    }
}
