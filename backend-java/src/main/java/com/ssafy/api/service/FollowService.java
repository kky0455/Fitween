package com.ssafy.api.service;

import com.ssafy.db.entity.User;

public interface FollowService {
    public void follow(User from, User to);
    public void unFollow(User from, User to);
    public boolean isFollow(User from, User to);

}
