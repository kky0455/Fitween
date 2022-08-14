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
public class RegionUpdateDto {

    @ApiModelProperty(name = "유저 id")
    String userIdx;


    @ApiModelProperty(name = "유저 사는 지역")
    @ApiParam(value = "유저 사는 지역", type = "String")
    String region;
}
