package com.app.api.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Post extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    private String writer;

    @OneToMany(mappedBy = "post")
    @OrderBy("modifiedAt desc")
    private final List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private final List<PostLike> likes = new ArrayList<>();

    public Post(String title, String contents, String username) {
        this.title = title;
        this.contents = contents;
        this.writer = username;
    }

    public void updateContents(String contents) {this.contents = contents;}

    public boolean isWriterMatch(String username){
        return this.writer.equals(username);
    }
}
