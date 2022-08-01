package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssafy.api.controller.SaveArticleDto;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @ManyToOne
    @JoinColumn(name = "user_idx")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

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
