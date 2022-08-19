package com.ssafy.api.service;

import com.ssafy.common.exception.handler.CustomApiException;
import com.ssafy.common.exception.handler.UserNotFoundException;
import com.ssafy.db.entity.Follow;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService{

    @Autowired
    private final FollowRepository followRepository;

    private final EntityManager em;

    public void follow(User from, User to) {
        if(followRepository.findByFromAndTo(from, to).orElse(null) != null) throw new CustomApiException("이미 팔로우 하였습니다.");
        followRepository.save(Follow.builder()
                .from(from)
                .to(to)
                .build());
    }
    public void unFollow(User from, User to) {
        Follow follow = followRepository.findByFromAndTo(from, to).orElse(null);
        followRepository.delete(follow);
    }
    public boolean isFollow(User from, User to){
        Follow follow = followRepository.findByFromAndTo(from, to).orElse(null);
        if (follow == null) {
            return false;
        }
        return true;
    }
}
