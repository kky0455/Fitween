package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@AttributeOverride(name = "id", column = @Column(name = "article_idx"))
public class ArticleInfo extends BaseEntity{
    String feedImg;
    String title;
    int price;
    String content;
    Boolean lend;
}
