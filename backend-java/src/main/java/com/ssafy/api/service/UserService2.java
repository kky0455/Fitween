package com.ssafy.api.service;

import com.ssafy.api.request.UserProfileDto;
import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.api.request.UserUpdateDto;
import com.ssafy.db.entity.User;

public interface UserService2 {
    User createUser(UserRegisterPostReq userRegisterInfo);
    User getUserByUserId(String userId);
    User getUserByUserIdx(Long userIdx);
    boolean checkUserId(String userId);
    boolean deleteByUserId(User user);
    void updateUser(UserUpdateDto updateUserDto);
    public UserProfileDto getUserProfileDto(Long profileId, Long userId);
}
