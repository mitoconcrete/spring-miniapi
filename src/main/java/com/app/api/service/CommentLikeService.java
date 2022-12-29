package com.app.api.service;

import com.app.api.dto.response.LikeResponseDto;
import com.app.api.dto.service.LikeServiceDto;
import com.app.api.entity.Comment;
import com.app.api.entity.CommentLike;
import com.app.api.entity.Post;
import com.app.api.entity.PostLike;
import com.app.api.exception.NotFoundException;
import com.app.api.repository.CommentLikeRepository;
import com.app.api.repository.CommentRepository;
import com.app.api.repository.PostLikeRepository;
import com.app.api.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentLikeService implements CommentLikeServiceInterface {
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;

    @Override
    public Long getLikes(Long commentId) {
        return commentLikeRepository.countAllByCommentIdAndIsRemovedFalse(commentId).orElseThrow(
                () -> new NotFoundException("게시물이 존재하지 않습니다.")
        );
    }

    private Optional<CommentLike> _getLike(LikeServiceDto likeServiceDto) {
        return commentLikeRepository.findByComment_IdAndUsername(likeServiceDto.getId(), likeServiceDto.getUsername());
    }

    private void _createLike(LikeServiceDto likeServiceDto) {
        Comment comment = commentRepository.findById(likeServiceDto.getId()).orElseThrow(
                () -> new NotFoundException("게시물이 존재하지 않습니다.")
        );
        CommentLike commentLike = new CommentLike(likeServiceDto.getUsername(), comment);
    }

    @Override
    public void toggleLike(LikeServiceDto likeServiceDto) {
        CommentLike commentLike = _getLike(likeServiceDto).orElse(null);
        if (commentLike == null) {
            _createLike(likeServiceDto);
        } else {
            commentLike.changeRemovedStatus(!commentLike.getIsRemoved());
        }
    }
}
