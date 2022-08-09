package com.ssafy.api.request;

import lombok.*;

@Builder
@Getter
public class UserProfileDto {

    private String userId;
    private String name;
    private int articleCount;
//    private String feedImg;
    private int userFollowerCount;
    private int userFollowingCount;
    private boolean isFollowed;

    public UserProfileDto(String userId, String name, int articleCount, int userFollowerCount, int userFollowingCount, boolean isFollowed) {
        this.userId = userId;
        this.name = name;
        this.articleCount = articleCount;
        this.userFollowerCount = userFollowerCount;
        this.userFollowingCount = userFollowingCount;
        this.isFollowed = isFollowed;
    }
}
