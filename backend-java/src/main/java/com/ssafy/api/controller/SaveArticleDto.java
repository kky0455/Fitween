package com.ssafy.api.controller;

import com.ssafy.db.entity.Article;
import com.ssafy.db.entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SaveArticleDto {

    @ApiModelProperty(name = "게시글 id")
    private Long article_idx;

    @ApiModelProperty(name = "게시글 제목")
    private String title;

    @ApiModelProperty(name = "가격")
    private int price;

    @ApiModelProperty(name = "내용")
    private String content;

//    @ApiModelProperty(name = "대여 가능 상태")
//    private boolean lend_status;

    @ApiModelProperty(name = "유저 idx")
    private Long user_idx;

    public Article toEntity(){
        User user = new User();
        user.setUserIdx(user_idx);
        return Article.builder()
                .title(title)
                .content(content)
                .price(price)
//                .lendStatus(lend_status)
                .user(user)
                .build();
    }
}
