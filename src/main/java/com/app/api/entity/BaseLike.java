package com.app.api.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.MappedSuperclass;
@Getter
@MappedSuperclass
@NoArgsConstructor
public class BaseLike extends BaseRemove {
    private String username;

    public BaseLike(String username){
        this.username = username;
    }
}
