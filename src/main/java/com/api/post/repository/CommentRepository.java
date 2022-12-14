package com.api.post.repository;

import com.api.post.entity.Comment;
import com.api.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByIdAndPost(Long commentId, Post post);
}
