package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ssafy.api.request.UpdateArticleDto;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Article{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    Long articleIdx;
    @Column(length = 60)
    String title;

    @Column
    String feedImg;

    @Column(length = 30)
    String content;

    @Column(name = "price")
    int price;

    @Column(name = "lend_status") // 대여 가능 상태
    @ColumnDefault("0")
    boolean lendStatus;

    @Transient
    private long likesCount;

    @Transient
    private boolean likesState;

    public void updateLikesCount(long likesCount) {
        this.likesCount = likesCount;
    }

    public void updateLikesState(boolean likesState) { this.likesState = likesState; }

    @JsonIgnoreProperties({"articles"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    private User user;

    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"articles", "user", "likes", "article", "like"})
    private List<Likes> likes = new ArrayList<>();

    public void updateArticle(UpdateArticleDto updateArticleDto) {
        this.title = updateArticleDto.getTitle();
        this.feedImg = updateArticleDto.getFeedImg();
        this.content = updateArticleDto.getContent();
        this.price = updateArticleDto.getPrice();
        this.lendStatus = updateArticleDto.isLendstatus();
    }
    @Builder
    public Article(String title, String feedImg, String content, int price, User user, int likesCount) {
        this.title = title;
        this.feedImg = feedImg;
        this.content = content;
        this.price = price;
        this.user = user;
        this.likesCount = likesCount;
    }
}
