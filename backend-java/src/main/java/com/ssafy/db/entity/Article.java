package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ssafy.api.request.SaveArticleDto;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Setter
@AllArgsConstructor
@Builder
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    Long articleIdx;

    @Column(length = 60)
    String title;

    @Column(length = 30)
    String content;

    @Column(name = "price")
    int price;

    @Column(columnDefinition = "TIMESTAMP")
    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "Asia/Seoul")
    @UpdateTimestamp
    LocalDateTime createdtime;

    @Column(name = "lend_status") // 대여 가능 상태
    @ColumnDefault("1")
    boolean lendStatus;

    @JsonIgnoreProperties("articles")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"article"})
    private List<Likes> likes = new ArrayList<>();

    @Transient
    private Long likesCount;

    @Transient
    private boolean likesState;

    public void updateArticle(SaveArticleDto saveArticleDto) {
        this.title = saveArticleDto.getTitle();
        this.content = saveArticleDto.getContent();
        this.price = saveArticleDto.getPrice();
    }

    @Override
    public String toString() {
        return "Board [articleIdx=" + articleIdx + ", title=" + title + ", content=" + content
                + ", price" + price + ", lendStatus=" + lendStatus
                + ", createdtime=" + createdtime + ", user=" + user + "]";
    }
}
