package com.ssafy.api.service;

import com.ssafy.api.controller.SaveArticleDto;
import com.ssafy.db.entity.Article;

import java.util.List;

public interface ArticleService {

    public Article createArticle(SaveArticleDto saveBoardDto);

    public Article findByArticleId(Long string);

    public Article updateArticle(Article article, SaveArticleDto saveArticleDto);

    public void deleteArticle(Article article);

    public List<Article> findAllArticle();
}
