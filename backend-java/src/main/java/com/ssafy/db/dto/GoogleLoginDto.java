package com.ssafy.db.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GoogleLoginDto {
    @ApiModelProperty(name="유저 ID", example="ssafy_web")
    String id;
    @ApiModelProperty(name="유저 Password", example="your_password")
    String password;
    @ApiModelProperty(name="로그인 타입", example = "login_type")
    String loginType;
    @ApiModelProperty(name = "token", example = "idToken")
    String token;

}
