package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ssafy.api.request.UpdateArticleDto;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Article extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_idx")
    Long articleIdx;

    @Column
    String title;

    @Column
    String content;

    @Column
    int price;

    @Column
    @ColumnDefault("0")
    boolean lendStatus;

    @Column
    @Enumerated(EnumType.STRING)
    Category category;

    @Transient
    private long likesCount;

    @Transient
    private boolean likeStatus;


    public void updateLikeStatus(boolean likeStatus) { this.likeStatus = likeStatus; }


    @JsonIgnoreProperties({"articles"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    private User user;

    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"articles", "user", "likes", "article", "like"})
    private List<Likes> likes = new ArrayList<>();

    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"article"})
    private List<ArticleImg> articleImgs = new ArrayList<>();

    public void updateArticle(UpdateArticleDto updateArticleDto){
        this.title = updateArticleDto.getTitle();
        this.content = updateArticleDto.getContent();
        this.price = updateArticleDto.getPrice();
        this.lendStatus = updateArticleDto.isLendstatus();
        this.category = updateArticleDto.getCategory();
    }
    @Builder
    public Article(String title, String content, int price, User user, int likesCount, Category category) {
        this.title = title;
        this.content = content;
        this.price = price;
        this.user = user;
        this.likesCount = likesCount;
        this.category = category;
    }
}
