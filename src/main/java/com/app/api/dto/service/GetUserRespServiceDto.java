package com.app.api.dto.service;

import com.app.api.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetUserRespServiceDto {
    private final Long id;
    private final String username;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public GetUserRespServiceDto(User user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.createdAt = user.getCreatedAt();
        this.modifiedAt = user.getModifiedAt();
    }

    public User toEntity(){
        return new User(this.id, this.username);
    }
}
