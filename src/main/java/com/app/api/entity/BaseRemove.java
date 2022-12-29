package com.app.api.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.MappedSuperclass;

@Getter
@MappedSuperclass
@NoArgsConstructor
public class BaseRemove {
    private Boolean isRemoved = false;

    public void changeRemovedStatus(Boolean status){
        this.isRemoved = status;
    }
}
