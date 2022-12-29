package com.app.api.entity;

import lombok.NoArgsConstructor;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseRemove {
    private Boolean isRemoved = false;

    public void changeRemovedStatus(){
        this.isRemoved = !this.isRemoved;
    }
}
