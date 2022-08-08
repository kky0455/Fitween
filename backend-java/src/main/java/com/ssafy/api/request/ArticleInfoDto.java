package com.ssafy.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleInfoDto {
    private Long id;
    private String title;
    private String content;
    private int price;
    private boolean likesState;
    private int likesCount;

}
