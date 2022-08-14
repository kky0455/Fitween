package com.ssafy.api.request;

import com.ssafy.db.entity.Article;
import com.ssafy.db.entity.ArticleImg;
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

    private int likesCount;
    private String writerId;
    private LocalDateTime lastUpdateTime;


    public ArticleInfoDto(Article article, boolean likeStatus, List<Object> articleImgs) {
//        article.getArticleImgs().forEach(articleImg -> {
//            String baseUrl = articleImg.getBaseUrl();
//            byte[] img = articleImg.getImg();
//            ArticleImgDto articleImgDto = new ArticleImgDto(baseUrl, img);
//            this.feedImg.add(articleImgDto);
//        });
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
