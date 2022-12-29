package com.app.api.repository;

import com.app.api.entity.CommentLike;
import com.app.api.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Optional<Long> countAllByPostIdAndIsRemovedFalse(Long postId);
    Optional<PostLike> findByPost_IdAndUsername(Long postId, String username);
}
