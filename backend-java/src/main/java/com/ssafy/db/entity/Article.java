package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class Article extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_idx")
    Long articleIdx;

    @Column
    String title;

    @Column
    String feedImg;

    @Column
    String content;

    @Column
    int price;

    @Column()
    @ColumnDefault("0")
    Boolean lendStatus;

    @Transient
    Long likesCount;

    @Transient
    boolean likesState;

    @JsonIgnoreProperties({"articles"})
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_idx")
    private User user;

    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"article"})
    private List<Likes> likes = new ArrayList<>();

    public void updateArticle(String title, String content, int price, boolean lendStatus){
        this.title = title;
        this.content = content;
        this.price = price;
        this.lendStatus = lendStatus;
    }

    public void updateLikesCount(Long likesCount) { this.likesCount = likesCount; }
    public void updateLikesState(boolean likesState) { this.likesState = likesState; }

    @Builder
    public Article(String title, String feedImg ,String content, int price, User user, Long likesCount) {
        this.title = title;
        this.feedImg = feedImg;
        this.content = content;
        this.price = price;
        this.user = user;
        this.likesCount = likesCount;
    }
}
