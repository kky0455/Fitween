package com.ssafy.api.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserInfoDto {
    String nickname;
    double weight;
    double height;
    String dateOfBirth;
    int footsize;
    String region;
    String gender;
}
