package com.ssafy.api.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
//@AllArgsConstructor
@NoArgsConstructor
public class SaveArticleDto {

    @ApiModelProperty(name = "게시글 제목")
    private String title;

    @ApiModelProperty(name = "가격")
    private int price;

    @ApiModelProperty(name = "내용")
    private String content;

}
