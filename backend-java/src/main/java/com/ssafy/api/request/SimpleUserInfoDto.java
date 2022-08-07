package com.ssafy.api.request;

import com.ssafy.db.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimpleUserInfoDto {

    private String userId;
    private String name;
    private boolean isFollowed;

    public void SimpleUserInfoDto(User user){
        this.userId = user.getUserId();
        this.name = user.getName();
    }

    public void SimpleUserInfoDto(User user, boolean isFollowed){
        this.userId = user.getUserId();
        this.name = user.getName();
        this.isFollowed = isFollowed;
    }
}
