package com.ssafy.api.request;

import com.ssafy.db.entity.Article;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ArticleInfoDto {
    private String title;
    private String feedImg;
    private String content;
    private int price;
    private boolean lendStatus;
    private String nickname;
    private boolean likeStatus;

    private int likesCount;
    private String writerId;

    private LocalDateTime lastUpdateTime;


    public ArticleInfoDto(Article article, boolean likeStatus) {
        this.title = article.getTitle();
//        this.feedImg = article.getFeedImg();
        this.content = article.getContent();
        this.price = article.getPrice();
        this.lendStatus = article.isLendStatus();
        this.nickname = article.getUser().getNickname();
        this.likeStatus = likeStatus;
        this.likesCount = article.getLikes().size();
        this.writerId = article.getUser().getUserId();
        this.lastUpdateTime = article.getLastUpdateTime();
    }

}
