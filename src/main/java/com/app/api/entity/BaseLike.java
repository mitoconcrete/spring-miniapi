package com.app.api.entity;

import lombok.NoArgsConstructor;

import javax.persistence.MappedSuperclass;
@MappedSuperclass
@NoArgsConstructor
public class BaseLike extends BaseRemove {
    private String username;

    public BaseLike(String username){
        this.username = username;
    }
}
