package com.api.post.entity;

import com.api.post.dto.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Post extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String writer;

    @Column(nullable = false)
    private String contents;

    public Post(PostRequestDto postRequestDto){
        this.title = postRequestDto.getTitle();
        this.writer = postRequestDto.getWriter();
        this.password = postRequestDto.getPassword();
        this.contents = postRequestDto.getContents();
    }

    public void updateContents(String contents) {this.contents = contents;}

    public boolean isValidPassword(String comparePassword) {return this.password.equals(comparePassword);}
}
