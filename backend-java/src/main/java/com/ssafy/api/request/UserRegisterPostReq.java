package com.ssafy.api.request;

import com.ssafy.db.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 유저 회원가입 API ([POST] /api/v1/users) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("UserRegisterPostRequest")
public class UserRegisterPostReq {
	@ApiModelProperty(name="유저 ID", example="ssafy_web")
	String id;
	@ApiModelProperty(name="유저 고유번호", example = "15612315684")
	String userId;
	@ApiModelProperty(name="유저 이름", example = "한세환")
	String name;
	@ApiModelProperty(name="유저 프사", example = "유저 프사 링크")
	String profileImg;
	@ApiModelProperty(name="유저 이메일", example = "ssafy123@gmail.com")
	String email;
	@ApiModelProperty(name="유저 나이", example = "28")
	int age;
	@ApiModelProperty(name="유저 생년월일", example = "yyyy/mm/dd")
	String dateOfBirth;
	@ApiModelProperty(name="유저 성별", example = "남성 / 여성")
	String gender;
	@ApiModelProperty(name="유저 닉네임", example = "씽코")
	String nickname;
	@ApiModelProperty(name="유저 키", example = "182")
	double height;
	@ApiModelProperty(name="유저 몸무게", example = "78")
	double weight;
	@ApiModelProperty(name="유저 신발사이즈", example = "280")
	int footSize;
	@ApiModelProperty(name="유저 사는 지역", example = "광주 서구 치평동")
	String region;

	public User toEntity(){
		User user = new User();
		user.setUserId(userId);
		user.setName(name);
		user.setEmail(email);
		user.setAge(age);
		user.setGender(gender);
		user.setNickname(nickname);
		user.setHeight(height);
		user.setWeight(weight);
		user.setFootSize(footSize);
		user.setRegion(region);
		return user;
	}
}
