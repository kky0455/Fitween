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

    @Lob
    @Column(columnDefinition = "BLOB")
    byte[] img;

    @Builder
    public ArticleImg(Article article, byte[] img){
        this.article = article;
        this.img = img;
    }
}
