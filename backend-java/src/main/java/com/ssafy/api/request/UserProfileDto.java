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

    public UserProfileDto(User user, int articleCount, int userFollowerCount, int userFollowingCount) {
        this.user = user;
        this.articleCount = articleCount;
        this.userFollowerCount = userFollowerCount;
        this.userFollowingCount = userFollowingCount;
    }
}
