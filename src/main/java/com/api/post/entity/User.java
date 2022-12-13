package com.api.post.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class User extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user")
    private final List<Post> posts = new ArrayList<>();


    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean isPasswordValid(String password) {
        return this.password.equals(password);
    }
}
