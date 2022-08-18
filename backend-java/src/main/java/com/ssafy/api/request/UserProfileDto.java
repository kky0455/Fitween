package com.ssafy.api.request;

import com.ssafy.db.entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class UserProfileDto {

    private String userId;
    private String nickname;
    private int articleCount;

    private int userFollowerCount;
    private int userFollowingCount;
    private boolean isFollowed;

    public UserProfileDto(String userId, String nickname, int articleCount, int userFollowerCount, int userFollowingCount, boolean isFollowed) {
        this.userId = userId;
        this.nickname = nickname;
        this.articleCount = articleCount;
        this.userFollowerCount = userFollowerCount;
        this.userFollowingCount = userFollowingCount;
        this.isFollowed = isFollowed;
    }
}
