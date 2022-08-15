package com.ssafy.api.request;

import com.ssafy.db.entity.Article;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.util.List;

@Getter
@Setter
public class GetArticleDto {

    double height;


    double weight;


    int footSize;


    String region;

    List<Article> articles;

}
