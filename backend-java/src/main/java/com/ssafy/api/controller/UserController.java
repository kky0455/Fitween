package com.ssafy.api.controller;

import com.ssafy.api.request.SimpleUserInfoDto;
import com.ssafy.api.request.UserProfileDto;
import com.ssafy.api.request.UserUpdateDto;
import com.ssafy.api.service.FollowService;
import com.ssafy.db.repository.FollowRepository;
import com.ssafy.db.repository.UserRepositorySupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.api.response.UserRes;
import com.ssafy.api.service.UserService;
import com.ssafy.common.auth.FWUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;
import java.util.NoSuchElementException;

/**
 * 유저 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "유저 API", tags = {"User"})
@RestController
@RequestMapping("/users")
public class UserController {

	private static final String SUCCESS = "success";
	public static final Logger logger = LoggerFactory.getLogger(UserController.class);

	UserRepositorySupport userRepositorySupport;

	@Autowired
	FollowService followService;

	@Autowired
	UserService userService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	FollowRepository followRepository;



	@PostMapping()
	@ApiOperation(value = "회원 가입", notes = "<strong>아이디와 패스워드</strong>를 통해 회원가입 한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 404, message = "사용자 없음"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<? extends BaseResponseBody> register(
			@RequestBody @ApiParam(value = "회원가입 정보", required = true) UserRegisterPostReq registerInfo) {

		//임의로 리턴된 User 인스턴스. 현재 코드는 회원 가입 성공 여부만 판단하기 때문에 굳이 Insert 된 유저 정보를 응답하지 않음.
		User user = userService.createUser(registerInfo);

		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
	}

//	@GetMapping("/me")
//	@ApiOperation(value = "회원 본인 정보 조회", notes = "로그인한 회원 본인의 정보를 응답한다.")
//	@ApiResponses({
//			@ApiResponse(code = 200, message = "성공"),
//			@ApiResponse(code = 401, message = "인증 실패"),
//			@ApiResponse(code = 404, message = "사용자 없음"),
//			@ApiResponse(code = 500, message = "서버 오류")
//	})
//	public ResponseEntity<UserRes> getUserInfo(@ApiIgnore Authentication authentication) {
//		/**
//		 * 요청 헤더 액세스 토큰이 포함된 경우에만 실행되는 인증 처리이후, 리턴되는 인증 정보 객체(authentication) 통해서 요청한 유저 식별.
//		 * 액세스 토큰이 없이 요청하는 경우, 403 에러({"error": "Forbidden", "message": "Access Denied"}) 발생.
//		 */
//		FWUserDetails userDetails = (FWUserDetails) authentication.getDetails();
//		String userId = userDetails.getUsername();
//		User user = userService.getUserByUserId(userId);
//
//		return ResponseEntity.status(200).body(UserRes.of(user));
//	}
//
//	@GetMapping("/{user_id}/profile")
//	public ResponseEntity<UserProfileDto> profile(@PathVariable("user_id") String user_id, @ApiIgnore Authentication authentication) {
//		FWUserDetails userDetails = (FWUserDetails) authentication.getDetails();
//		Long userIdx = userDetails.getUserIdx();
//		Long profileIdx = userRepositorySupport.findUserByUserId(user_id).get().getUserIdx();
//		UserProfileDto userProfileDto = userService.getUserProfileDto(profileIdx, userIdx);
//		return ResponseEntity.ok().body(userProfileDto);
//	}

	@ApiOperation(value = "사용자의 상세 정보를 반환한다.", response = User.class)
	@GetMapping("/{user_id}")
	public ResponseEntity<?> findUser(@PathVariable String user_id, @ApiIgnore Authentication authentication){
		User user = userService.getUserByUserId(user_id);
		int articleCount = user.getArticles().size();
		int followerCount = followRepository.findFollowerCountById(user.getUserIdx());
		int followingCount = followRepository.findFollowingCountById(user.getUserIdx());
		UserProfileDto userProfileDto = new UserProfileDto(user, articleCount, followerCount, followingCount);
		return new ResponseEntity<>(userProfileDto, HttpStatus.OK);
	}

	@GetMapping("/check/{user_id}")
	@ApiOperation(value = "아이디 중복체크", notes = "회원가입 시 아이디 중복체크")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 404, message = "사용자 없음"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<Boolean> idCheck(@PathVariable("user_id") String userId) {
		boolean tmp = userService.checkUserId(userId);
		System.out.println(tmp);
		if (tmp == true) {
			System.out.println("중복된 id가 없습니다.");
			return ResponseEntity.status(200).body(userService.checkUserId(userId));
		} else System.out.println("id 중복이 있습니다.");
		return ResponseEntity.status(401).body(userService.checkUserId(userId));
	}

	// 회원 정보 수정
	@ApiOperation(value = "회원 정보 수정")
	@PutMapping("/update")
	public ResponseEntity<String> update(@RequestBody UserUpdateDto updateUserDto) throws Exception {
		User user;
		try {
			userService.getUserByUserId(updateUserDto.getId());
		} catch (NoSuchElementException E) {
			System.out.println("회원 수정 실패");
			return ResponseEntity.status(500).body("등록된 회원이 없습니다.");
		}
		userService.updateUser(updateUserDto);
		System.out.println("업데이트 됨");
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}

	// 회원탈퇴
	@ApiOperation(value = "회원 탈퇴")
	@ApiResponses({@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 404, message = "사용자 없음"),
			@ApiResponse(code = 500, message = "해당 회원 없음")})
	@DeleteMapping("/remove/{user_id}")
	public ResponseEntity<String> userdelete(@PathVariable("user_id")String id) throws Exception {
		boolean result;
		try {
			User user = userService.getUserByUserId(id);
			result = userService.deleteByUserId(user);
			System.out.println(result);
		} catch (NoSuchElementException E) {
			System.out.println("회원 탈퇴 실패");
			return ResponseEntity.status(500).body("해당 회원 없어서 회원 탈퇴 실패");
		}
		logger.debug("회원 탈퇴 성공");
		return ResponseEntity.status(200).body("회원 탈퇴 성공");
	}
	// 회원 비밀번호 변경을 위한 비밀번호 체크
	@GetMapping("/password")
	@ApiOperation(value = "회원 비밀번호 체크 (token)", notes = "로그인한 회원 본인의 정보를 응답한다.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 404, message = "사용자 없음"),
			@ApiResponse(code = 500, message = "서버 오류") })

	public ResponseEntity<String> checkUserPassword(@RequestParam String password, @ApiIgnore Authentication authentication) {
		/**
		 * 요청 헤더 액세스 토큰이 포함된 경우에만 실행되는 인증 처리이후, 리턴되는 인증 정보 객체(authentication) 통해서 요청한 유저
		 * 식별. 액세스 토큰이 없이 요청하는 경우, 403 에러({"error": "Forbidden", "message": "Access
		 * Denied"}) 발생.
		 */
		FWUserDetails userDetails = (FWUserDetails) authentication.getDetails();
		String userId = userDetails.getUsername();
		User user = userService.getUserByUserId(userId);

		if(passwordEncoder.matches(password, user.getPassword())) {
			// 유효한 패스워드가 맞는 경우, 로그인 성공으로 응답.(액세스 토큰을 포함하여 응답값 전달)
			return ResponseEntity.status(200).body("Success");
		}
		return ResponseEntity.status(401).body("Invalid Password");
	}



}