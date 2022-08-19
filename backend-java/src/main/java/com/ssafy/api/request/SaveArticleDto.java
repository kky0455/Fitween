package com.ssafy.api.request;

import com.ssafy.db.entity.Category;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveArticleDto {

    @ApiModelProperty(name = "게시글 제목")
    private String title;

    @ApiModelProperty(name = "게시글 이미지")
    List<String> photos;

    @ApiModelProperty(name = "내용")
    private String content;

    @ApiModelProperty(name = "가격")
    private int price;

    @ApiModelProperty(name = "카테고리")
    private Category category;

}
