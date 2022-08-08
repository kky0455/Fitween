package com.ssafy.api.service;

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
