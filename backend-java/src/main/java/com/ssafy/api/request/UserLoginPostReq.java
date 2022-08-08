package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 유저 로그인 API ([POST] /api/v1/auth/login) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@Builder
@ApiModel("UserLoginPostRequest")
public class UserLoginPostReq {

	@ApiModelProperty(example="발급받은 access token")
	String accessToken;
	@ApiModelProperty(example="발급받은 refresh token")
	String refreshToken;
	@ApiModelProperty(example="user_idx 번호")
	Long id;
	@ApiModelProperty(example="156461325451")
	String userId;
	@ApiModelProperty(example="han123@gmail.com")
	String email;
	@ApiModelProperty(example="유저 닉네임")
	String nickname;
	@ApiModelProperty(example="20")
	int age ;
	@ApiModelProperty(example="1=Male, 2=Female, 3=Unknown")
	int gender;
	@ApiModelProperty(example="182.1")
	double height ;
	@ApiModelProperty(example="80.5")
	double weight ;
	@ApiModelProperty(example="250")
	int footSize ;
	@ApiModelProperty(example="광주 광산구 장덕동")
	String region;

}
