package com.ssafy.api.request;

import com.ssafy.db.entity.Category;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ArticleRecommendDto {
    @ApiModelProperty(name = "게시글 인덱스")
    private Long articleIdx;
    @ApiModelProperty(name = "게시글 제목")
    private String title;
    @ApiModelProperty(name = "게시글 이미지")
    List<Object> feedArticleImg;
    @ApiModelProperty(name = "게시글 가격")
    private int price;
    @ApiModelProperty(name = "대여 가능 여부")
    private boolean lendStatus;
    @ApiModelProperty(name = "작성자 별명")
    private String nickname;
    @ApiModelProperty(name = "좋아요 여부")
    private boolean likeStatus;
    @ApiModelProperty(name = "좋아요 수")
    private int likesCount;
    @ApiModelProperty(name = "작성자 ID값")
    private String userId;

}
