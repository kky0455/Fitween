package com.ssafy.api.controller;

import com.ssafy.api.request.UserProfileDto;
import com.ssafy.api.request.UserUpdateDto;
import com.ssafy.api.service.FollowService;
import com.ssafy.db.repository.FollowRepository;
import com.ssafy.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.ssafy.api.request.UserRegisterPostReq;
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

/**
 * 유저 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "유저 API", tags = {"User"})
@RestController
@RequestMapping("/users")
@ApiResponses({
		@ApiResponse(code = 200, message = "성공"),
		@ApiResponse(code = 401, message = "인증 실패"),
		@ApiResponse(code = 404, message = "사용자 없음"),
		@ApiResponse(code = 500, message = "서버 오류")
})
public class UserController {
	@Autowired
	FollowService followService;
	@Autowired
	UserService userService;
	@Autowired
	FollowRepository followRepository;
	@Autowired
	UserRepository userRepository;

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
	@ApiOperation(value = "사용자의 상세 정보를 반환한다.", response = User.class)
	@GetMapping("/{user_id}")
	public ResponseEntity<?> findUser(@PathVariable String user_id, @ApiIgnore Authentication authentication){

		FWUserDetails userDetails = (FWUserDetails) authentication.getDetails();

		User user = userService.getUserByUserId(user_id);
		int articleCount = user.getArticles().size();
		int followingCount = followRepository.countByFrom(user);
		int followerCount = followRepository.countByTo(user);
		boolean isFollowed = followService.isFollow(user, userDetails.getUser());
		UserProfileDto userProfileDto = new UserProfileDto(user.getUserId(), user.getName(), articleCount, followerCount, followingCount, isFollowed);
		return ResponseEntity.status(200).body(userProfileDto);
	}
	@ApiOperation(value = "회원 정보 수정")
	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestBody UserUpdateDto updateUserDto, @ApiIgnore Authentication authentication) throws Exception {
		FWUserDetails userDetails = (FWUserDetails) authentication.getDetails();
		userService.updateUser(userDetails.getUserIdx(), updateUserDto);
		return ResponseEntity.status(200).body("회원정보 수정 완료");
	}
	@ApiOperation(value = "회원 탈퇴")
	@DeleteMapping("/remove")
	public ResponseEntity<String> userdelete(@ApiIgnore Authentication authentication) throws Exception {
		FWUserDetails userDetails = (FWUserDetails) authentication.getDetails();
		User user = userDetails.getUser();
		userRepository.delete(user);
		return ResponseEntity.status(200).body("회원 탈퇴 성공");
	}
}