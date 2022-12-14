package com.api.post.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Comment(Post post, User user, String contents) {
        this.post = post;
        this.user = user;
        this.contents = contents;
    }

    public void updateContents(String contents) {
        this.contents = contents;
    }

    public boolean isAuthor(User user){
        return this.user.equals(user);
    }
}
