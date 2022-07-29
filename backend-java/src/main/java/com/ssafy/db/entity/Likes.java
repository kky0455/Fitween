package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@NoArgsConstructor
public class Likes extends BaseEntity{

    @JoinColumn(name = "article_idx")
    @ManyToOne
    ArticleInfo articleInfo;

    @JoinColumn(name = "user_idx")
    @ManyToOne
    User user;
}
