package com.ssafy.api.request;

import com.ssafy.db.entity.Article;
import com.ssafy.db.entity.ArticleImg;
import com.ssafy.db.entity.Category;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

@Getter
@Setter
public class ArticleInfoDto {
    private String title;
    List<Object> feedImg;
    private String content;
    private int price;
    private boolean lendStatus;
    private String nickname;
    private boolean likeStatus;

    private Category category;

    private int likesCount;
    private String writerId;
    private LocalDateTime lastUpdateTime;


    public ArticleInfoDto(Article article, boolean likeStatus, List<Object> articleImgs) {
        this.category = article.getCategory();
        this.feedImg = articleImgs;
        this.title = article.getTitle();
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
