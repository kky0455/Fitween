package com.ssafy.api.request;

import com.ssafy.db.entity.Article;
import com.ssafy.db.entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveArticleDto {

    @ApiModelProperty(name = "게시글 제목")
    private String title;

    @ApiModelProperty(name = "게시글 제목")
    private String feedImg;

    @ApiModelProperty(name = "내용")
    private String content;

    @ApiModelProperty(name = "가격")
    private int price;
}
