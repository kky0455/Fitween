package com.ssafy.api.service;

import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.api.request.UserUpdateDto;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.db.entity.User;
import com.ssafy.db.repository.UserRepository;

import javax.transaction.Transactional;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	PasswordEncoder passwordEncoder;


	public User createUser(UserRegisterPostReq userRegisterInfo) {

		User user = new User();
		user.setUserId(userRegisterInfo.getId());
		user.setName(userRegisterInfo.getName());
//		user.setProfileImg(userRegisterInfo.getProfileImg());
//		user.setEmail(userRegisterInfo.getEmail());
//		user.setAge(userRegisterInfo.getAge());
//		user.setGender();
//		user.setNickname(userRegisterInfo.getNickname());
//		user.setHeight(userRegisterInfo.getHeight());
//		user.setWeight(userRegisterInfo.getWeight());
//		user.setFootSize(userRegisterInfo.getFootSize());
//		user.setRegion(userRegisterInfo.getRegion());
		// 보안을 위해서 유저 패스워드 암호화 하여 디비에 저장.
		user.setPassword(passwordEncoder.encode(userRegisterInfo.getPassword()));
		return userRepository.save(user);
	}

	private final UserRepository userRepository;
	@Override
	public User getUserByUserId(String userId) {
		// 디비에 유저 정보 조회 (userId 를 통한 조회).
		User user = userRepository.findUserByUserId(userId).orElseGet(null);
		return user;
	}

	@Override
	public User getUserByUserIdx(Long userIdx) {
		// 디비에 유저 정보 조회 (userId 를 통한 조회).
		User user = userRepository.findByUserIdx(userIdx).orElseGet(null);
		return user;
	}
	@Override
	public void updateUser(Long userIdx, UserUpdateDto userUpdateDto) {
		User user = userRepository.findByUserIdx(userIdx).orElse(null);
		user.updateUser(userUpdateDto);
	}
}
