package com.ssafy.api.request;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(name = "유저 password")
    String password;
    @ApiModelProperty(name = "유저 이름")
    String name;
}
