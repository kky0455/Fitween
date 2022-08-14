package com.ssafy.db.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_img_idx")
    private Long articleImgIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_idx")
    Article article;

    @Column(columnDefinition = "TEXT")
    String articleImg;

    @Builder
    public ArticleImg(Article article, String articleImg){
        this.article = article;
        this.articleImg = articleImg;
    }
}
