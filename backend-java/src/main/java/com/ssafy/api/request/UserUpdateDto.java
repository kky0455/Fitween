package com.ssafy.api.request;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDto {

    @ApiModelProperty(name = "유저 id")
    String id;

    @ApiModelProperty(name = "유저 id")
    @ApiParam(value = "유저 id", type = "String")
    String userId;

    @ApiModelProperty(name = "유저 이름")
    @ApiParam(value = "유저 이름", type = "String")
    String name;

    @ApiModelProperty(name = "유저 프로필사진")
    @ApiParam(value = "유저 프로필사진(주소)", type = "String")
    String profileImg;

    @ApiModelProperty(name = "유저 이메일")
    @ApiParam(value = "유저 email", type = "String")
    String email;

    @ApiModelProperty(name = "유저 나이")
    @ApiParam(value = "유저 나이", type = "int")
    int age;

    @ApiModelProperty(name = "유저 성별")
    @ApiParam(value = "유저 성별", type = "int")
    int gender;

    @ApiModelProperty(name = "유저 닉네임")
    @ApiParam(value = "유저 닉네임", type = "String")
    String nickname;

    @ApiModelProperty(name = "유저 키")
    @ApiParam(value = "유저 키", type = "double")
    double height;

    @ApiModelProperty(name = "유저 몸무게")
    @ApiParam(value = "유저 몸무게", type = "double")
    double weight;

    @ApiModelProperty(name = "유저 발사이즈")
    @ApiParam(value = "유저 발사이즈", type = "int")
    int footSize;

    @ApiModelProperty(name = "유저 사는 지역")
    @ApiParam(value = "유저 사는 지역", type = "String")
    String region;
}
