package com.ssafy.api.service;

import com.ssafy.api.request.UserProfileDto;
import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.api.request.UserUpdateDto;
import com.ssafy.db.entity.User;

import java.util.List;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface UserService {
	User createUser(UserRegisterPostReq userRegisterInfo);
	User getUserByUserId(String userId);
	User getUserByUserIdx(Long userIdx);
	boolean checkUserId(String userId);
	boolean deleteByUserId(User user);
	void updateUser(UserUpdateDto updateUserDto);
	public UserProfileDto getUserProfileDto(Long profileId, Long userId);


}
