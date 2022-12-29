package com.app.api.service;

import com.app.api.dto.response.LikeResponseDto;
import com.app.api.dto.service.LikeServiceDto;

public interface CommentLikeServiceInterface {
    void toggleLike(LikeServiceDto likeServiceDto);
}
