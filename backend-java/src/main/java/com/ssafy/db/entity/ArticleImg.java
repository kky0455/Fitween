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

    @Column
    String baseUrl;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    byte[] img;

    @Builder
    public ArticleImg(Article article, String baseUrl, byte[] img){
        this.article = article;
        this.baseUrl = baseUrl;
        this.img = img;
    }
}
