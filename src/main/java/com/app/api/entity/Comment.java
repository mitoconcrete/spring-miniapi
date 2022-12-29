package com.app.api.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String contents;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    private String writer;

    @OneToMany(mappedBy = "comment")
    private final List<CommentLike> likes = new ArrayList<>();

    public Comment(Post post, String username, String contents) {
        this.post = post;
        this.writer = username;
        this.contents = contents;
    }

    public void updateContents(String contents) {
        this.contents = contents;
    }

    public boolean isWriterMatch(String username){
        return this.writer.equals(username);
    }
}
