package com.ssafy.db.dto;

import com.ssafy.db.entity.User;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSaveRequestDto {
    @ApiModelProperty(example="han123@gmail.com")
    @ApiParam(value = "사용자 email", type = "String")
    private String email;

    @Builder
    public UserSaveRequestDto(String email){
        this.email = email;
    }

    public User toEntity(){
        User user = new User();
        user.setEmail(email);
        return user;
    }
}
