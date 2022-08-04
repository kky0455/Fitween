package com.ssafy.api.service;

import com.ssafy.api.request.UserLoginPostReq;

import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.api.request.UserUpdateDto;
import com.ssafy.common.auth.JwtTokenProvider;
import com.ssafy.db.dto.UserDto;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.UserRepository;
import com.ssafy.db.repository.UserRepository2;
import com.ssafy.db.repository.UserRepositorySupport;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;


/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final UserRepository2 userRepository2;
	private final UserRepositorySupport userRepositorySupport;
	private final JwtTokenProvider jwtTokenProvider;

	PasswordEncoder passwordEncoder;

	public User createUser(UserRegisterPostReq userRegisterInfo) {

		User user = new User();
		user.setUserId(userRegisterInfo.getId());
		user.setName(userRegisterInfo.getName());
		user.setProfileImg(userRegisterInfo.getProfileImg());
		user.setEmail(userRegisterInfo.getEmail());
		user.setAge(userRegisterInfo.getAge());
		user.setGender(userRegisterInfo.getGender());
		user.setNickname(userRegisterInfo.getNickname());
		user.setHeight(userRegisterInfo.getHeight());
		user.setWeight(userRegisterInfo.getWeight());
		user.setFootSize(userRegisterInfo.getFootSize());
		user.setRegion(userRegisterInfo.getRegion());
		//보안을 위해서 유저 패스워드 암호화 하여 디비에 저장.
		user.setPassword(passwordEncoder.encode(userRegisterInfo.getPassword()));
		return UserRepository2.save(user);
	}

	public User getUserByUserId(String userId) {
		// 디비에 유저 정보 조회 (userId 를 통한 조회).
		User user = userRepositorySupport.findUserByUserId(userId).get();
		return user;
	}

	public boolean checkUserId(String userId) {
		boolean result = userRepositorySupport.findByUserIdEquals(userId);
		return result;
	}

//	public boolean deleteByUserId(User user) {
//		UserRepository2.delete(user);
//		return true;
//	}

	@Transactional
	public void updateUser(UserUpdateDto userUpdateDto) {
		User user = userRepositorySupport.findUserByUserId(userUpdateDto.getId()).get();
		String password = passwordEncoder.encode(userUpdateDto.getPassword());
		user.updateUser(userUpdateDto.getName(), password);
	}

	@Transactional
	public UserLoginPostReq userLogin(String email, String password) throws Exception {
		User user = userRepository.findByEmail(email);

		// 리프레쉬 토큰 발급
		user.changeRefreshToken(jwtTokenProvider.createRefreshToken(email, user.getRoles()));
		UserLoginPostReq userLoginPostReq = UserLoginPostReq.builder()
				.email(email)
				.accessToken(jwtTokenProvider.createToken(email, user.getRoles()))
				.refreshToken(user.getRefreshToken())
				.userId(user.getUserId()).nickname(user.getNickname())
				.gender(user.getGender()).age(user.getAge())
				.height(user.getHeight()).weight(user.getWeight())
				.build();

		return userLoginPostReq;
	}

	@Transactional
	public UserLoginPostReq refreshToken(String token, String refreshToken) throws Exception {

		//if(userRepository.isLogout(jwtTokenProvider.getUserPk(token))) throw new AccessDeniedException("");
		// 아직 만료되지 않은 토큰으로는 refresh 할 수 없음
		if(jwtTokenProvider.validateToken(token)) throw new AccessDeniedException("token이 만료되지 않음");

		User user = userRepository.findByEmail(jwtTokenProvider.getUserPk(refreshToken));
		System.out.println(user.getRefreshToken());
		if(!refreshToken.equals(user.getRefreshToken())) throw new AccessDeniedException("해당 멤버가 존재하지 않습니다.");

		if(!jwtTokenProvider.validateToken(user.getRefreshToken()))
			throw new IllegalStateException("다시 로그인 해주세요.");

		user.changeRefreshToken(jwtTokenProvider.createRefreshToken(user.getEmail(), user.getRoles()));

		UserLoginPostReq userDto = UserLoginPostReq.builder()
				.email(user.getEmail())
				.accessToken(jwtTokenProvider.createToken(user.getEmail(), user.getRoles()))
				.refreshToken(user.getRefreshToken())
				.userId(user.getUserId()).nickname(user.getNickname())
				.gender(user.getGender()).age(user.getAge())
				.height(user.getHeight()).weight(user.getWeight())
				.build();

		return userDto;
	}

	@Transactional
	public void joinSocial(UserDto userDto){
		User user = new User();
		user.setEmail(userDto.getEmail());
		user.setName(userDto.getName());
		user.setPassword("social");
		userRepository.saveUser(user);
	}

	@Transactional
	public void socialLogin(String email, String refreshToken){
		userRepository.socialLogin(email, refreshToken);
	}
}
