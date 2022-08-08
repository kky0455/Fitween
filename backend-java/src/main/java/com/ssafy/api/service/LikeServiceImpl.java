package com.ssafy.api.service;

import com.ssafy.common.exception.handler.CustomApiException;
import com.ssafy.db.repository.LikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService{

    private final LikesRepository likesRepository;

    @Transactional
    public void likes(Long articleIdx, Long userIdx) {
        try {
            likesRepository.likes(articleIdx, userIdx);
        } catch (Exception e) {
            throw new CustomApiException("이미 좋아요 하셨습니다.");
        }
    }

    @Transactional
    public void unLikes(Long articleIdx, Long userIdx) {
        likesRepository.unLikes(articleIdx, userIdx);
    }
}
