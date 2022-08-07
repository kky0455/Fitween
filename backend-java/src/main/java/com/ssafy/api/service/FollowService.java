package com.ssafy.api.service;

import com.ssafy.api.request.FollowDto;
import com.ssafy.api.request.SimpleUserInfoDto;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.FollowRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface FollowService {
    public void follow(String fromUserId, String toUserId);

    public void unFollow(Long fromUserId, Long toUserId);

//    public List<FollowDto> getFollower(String profileId, String userId);
//
//    public List<FollowDto> getFollowing(String profileId, String userId);



//    public Long follow(String fromUserId, String toUserId);
//    public void unfollow(String fromUserId, String toUserId);
//    public Optional<SimpleUserInfoDto> findFollowingList(String currentUserId, String userId);
//    public Optional<SimpleUserInfoDto> findFollowerList(String currentUserId, String userId);
//    public int getSizeofFollowers(String userId);
//    public int getSizeofFollowings(String userId);

}
