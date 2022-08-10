package com.ssafy.api.service;

import com.ssafy.api.request.UserLoginPostReq;

import com.ssafy.api.request.UserUpdateDto;
import com.ssafy.common.auth.JwtTokenProvider;
import com.ssafy.db.dto.UserDto;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.UserRepository;
import com.ssafy.db.repository.UserRepository2;
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
	private final JwtTokenProvider jwtTokenProvider;

	PasswordEncoder passwordEncoder;



	public User getUserByUserId(String userId) {
		// 디비에 유저 정보 조회 (userId 를 통한 조회).
		User user = userRepository2.findUserByUserId(userId).orElseGet(null);
		return user;
	}


	public User getUserByUserIdx(Long userIdx) {
		// 디비에 유저 정보 조회 (userId 를 통한 조회).
		User user = userRepository2.findByUserIdx(userIdx).orElseGet(null);
		return user;
	}

	public void updateUser(Long userIdx, UserUpdateDto userUpdateDto) {
		User user = userRepository2.findByUserIdx(userIdx).orElse(null);
		user.updateUser(userUpdateDto);
	}

	@Transactional
	public String join(User user){
		checkIdDuplicate(user.getUserId()); // 중복 회원 검증
		user.setEnable(false);
		userRepository.save(user);
		return user.getUserId();
	}

	@Transactional
	public void checkIdDuplicate(String userId) {
		boolean userIdDuplicate = userRepository.existsByUserId(userId);
		if(userIdDuplicate) throw new IllegalStateException("이미 존재하는 회원입니다.");

	}

	@Transactional
	public UserLoginPostReq userLogin(String userId) throws Exception {
		User user = getUserByUserId(userId);

		// 리프레쉬 토큰 발급
		user.changeRefreshToken(jwtTokenProvider.createRefreshToken(userId, user.getRoles()));
		UserLoginPostReq userLoginPostReq = UserLoginPostReq.builder()
//				.userId(userId)
				.accessToken(jwtTokenProvider.createToken(userId, user.getRoles()))
				.refreshToken(user.getRefreshToken())
//				.nickname(user.getNickname())
//				.gender(user.getGender()).age(user.getAge())
//				.height(user.getHeight()).weight(user.getWeight())
//				.region(user.getRegion()).email(user.getEmail())
//				.footSize(user.getFootSize())
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
		userRepository.save(user);
	}

	@Transactional
	public void socialLogin(String email, String refreshToken){
		userRepository.socialLogin(email, refreshToken);
	}
}
