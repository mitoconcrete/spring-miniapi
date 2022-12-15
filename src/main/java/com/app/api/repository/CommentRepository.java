package com.app.api.repository;

import com.app.api.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByIdAndPost_Id(Long commentId, Long postId);
}
