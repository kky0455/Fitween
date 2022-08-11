package com.ssafy.api.request;

import com.ssafy.db.entity.Article;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleInfoDto {
    private String title;
    private String feedImg;
    private String content;
    private int price;
    private boolean lendStatus;
    private String name;
    private boolean islike;
    private int likesCount;


    public ArticleInfoDto(Article article, boolean islike) {
        this.title = article.getTitle();
//        this.feedImg = article.getFeedImg();
        this.content = article.getContent();
        this.price = article.getPrice();
        this.lendStatus = article.isLendStatus();
        this.name = article.getUser().getName();
        this.islike = islike;
        this.likesCount = article.getLikes().size();
    }

}
