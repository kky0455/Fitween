package com.ssafy.api.service;

import com.ssafy.api.request.UserProfileDto;
import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.api.request.UserUpdateDto;
import com.ssafy.db.entity.Follow;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.FollowRepository;
import com.ssafy.db.repository.UserRepository2;
import com.ssafy.db.repository.UserRepositorySupport;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService2 {

	private final UserRepository2 userRepository;

	private final UserRepositorySupport userRepositorySupport;

	private final FollowRepository followRepository;

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
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

	@Override
	public User getUserByUserId(String userId) {
		// 디비에 유저 정보 조회 (userId 를 통한 조회).
		User user = userRepositorySupport.findUserByUserId(userId).get();
		return user;
	}

	@Override
	public User getUserByUserIdx(Long userIdx) {
		// 디비에 유저 정보 조회 (userId 를 통한 조회).
		User user = userRepository.findByUserIdx(userIdx).get();
		return user;
	}

	@Override
	public boolean checkUserId(String userId) {
		boolean result = userRepositorySupport.findByUserIdEquals(userId);
		return result;
	}

	@Override
	public boolean deleteByUserId(User user) {
		userRepository.delete(user);
		return true;
	}

	@Transactional
	@Override
	public void updateUser(UserUpdateDto userUpdateDto) {
		User user = userRepositorySupport.findUserByUserId(userUpdateDto.getId()).get();
//		String password = passwordEncoder.encode(userUpdateDto.getPassword());
		user.updateUser(userUpdateDto);
	}
	@Transactional
	@Override
	public UserProfileDto getUserProfileDto(Long profileId, Long userId) {
		User user = userRepository.findByUserIdx(profileId).get();
		int articleCount = user.getArticles().size();
		User loginUser = userRepository.findByUserIdx(userId).get();
		Follow follow = followRepository.findByUserIdAndTargetUserId(profileId, userId).orElse(null);
		boolean LoginUser = user.getUserIdx().equals(loginUser.getUserIdx());
		boolean isFollow = follow!=null;
		int followerCount = followRepository.findFollowerCountById(user.getUserIdx());
		int followingCount = followRepository.findFollowingCountById(user.getUserIdx());

//		user.getArticles().forEach(article -> {
//			article.up
//		});
		return new UserProfileDto(user, articleCount, followerCount, followingCount, true);

	}
}
