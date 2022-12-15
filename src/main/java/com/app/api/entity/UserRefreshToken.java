package com.app.api.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@RequiredArgsConstructor
public class UserRefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    @OneToOne
    private User user;

    public boolean isTokenValid(String token){
        return this.token.equals(token);
    }

    public void updateToken(String token){
        this.token = token;
    }
}
