package com.ssafy.api.request;

import com.ssafy.db.entity.User;
import lombok.*;

@Builder
@Getter
public class UserProfileDto {

    private User user;
    private int articleCount;
    private int userFollowerCount;
    private int userFollowingCount;
    private boolean isFollowed;

    public UserProfileDto(User user, int articleCount, int userFollowerCount, int userFollowingCount, boolean isFollowed) {
        this.user = user;
        this.articleCount = articleCount;
        this.userFollowerCount = userFollowerCount;
        this.userFollowingCount = userFollowingCount;
        this.isFollowed = isFollowed;
    }
}
