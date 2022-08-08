package com.ssafy.api.service;

public interface LikeService {
    public void likes(Long articleIdx, Long userIdx);

    public void unLikes(Long articleIdx, Long userIdx);
}
