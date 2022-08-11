package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ssafy.api.request.UpdateArticleDto;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Article{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_idx")
    Long articleIdx;

    @Column
    String title;

//    @Column
//    String feedImg;

    @Column
    String content;

    @Column
    int price;

    @Column()
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

    public void updateArticle(UpdateArticleDto updateArticleDto){
        this.title = updateArticleDto.getTitle();
//        this.feedImg = updateArticleDto.getFeedImg();
        this.content = updateArticleDto.getContent();
        this.price = updateArticleDto.getPrice();
        this.lendStatus = updateArticleDto.isLendstatus();
    }
    @Builder
    public Article(String title, String feedImg ,String content, int price, User user, int likesCount) {
        this.title = title;
//        this.feedImg = feedImg;
        this.content = content;
        this.price = price;
        this.user = user;
        this.likesCount = likesCount;
    }
}
