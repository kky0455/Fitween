package com.ssafy.api.service;

import com.ssafy.api.request.FollowDto;
import com.ssafy.common.exception.handler.CustomApiException;
import com.ssafy.common.exception.handler.UserNotFoundException;
import com.ssafy.db.entity.Follow;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.FollowRepository;
import com.ssafy.db.repository.UserRepositorySupport;
import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService{

    @Autowired
    private final FollowRepository followRepository;

    @Autowired
    private final UserRepositorySupport userRepositorySupport;

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
