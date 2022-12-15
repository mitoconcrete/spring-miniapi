package com.app.api.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class UserRefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    @OneToOne
    private User user;

    public UserRefreshToken(String token, User user){
        this.token = token;
        this.user = user;
    }

    public boolean isTokenValid(String token){
        return this.token.equals(token);
    }

    public void updateToken(String token){
        this.token = token;
    }
}
