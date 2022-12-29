package com.app.api.service;

import com.app.api.dto.response.LikeResponseDto;
import com.app.api.dto.service.LikeServiceDto;
import com.app.api.entity.Post;
import com.app.api.entity.PostLike;
import com.app.api.exception.NotFoundException;
import com.app.api.repository.PostLikeRepository;
import com.app.api.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostLikeService implements PostLikeServiceInterface{

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

    @Override
    public Long getLikes(Long postId) {
        return postLikeRepository.countAllByPostIdAndIsRemovedFalse(postId).orElseThrow(
                () -> new NotFoundException("게시물이 존재하지 않습니다.")
        );
    }

    private Optional<PostLike> _getLike(LikeServiceDto likeServiceDto){
        return postLikeRepository.findByPost_IdAndUsername(likeServiceDto.getId(), likeServiceDto.getUsername());
    }

    private void _createLike(LikeServiceDto likeServiceDto) {
        Post post = postRepository.findById(likeServiceDto.getId()).orElseThrow(
                () -> new NotFoundException("게시물이 존재하지 않습니다.")
        );
        PostLike postLike = new PostLike(likeServiceDto.getUsername(), post);
    }

    @Override
    public void toggleLike(LikeServiceDto likeServiceDto) {
        PostLike postLike = _getLike(likeServiceDto).orElse(null);
        if(postLike == null){
            _createLike(likeServiceDto);
        }else{
            postLike.changeRemovedStatus(!postLike.getIsRemoved());
        }
    }
}
