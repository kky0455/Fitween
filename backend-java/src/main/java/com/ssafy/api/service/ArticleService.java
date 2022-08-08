package com.ssafy.api.service;

import com.ssafy.api.request.SaveArticleDto;
import com.ssafy.api.request.UpdateArticleDto;
import com.ssafy.db.entity.Article;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ArticleService {

    public void createArticle(SaveArticleDto saveBoardDto, Authentication authentication);

    public Article findByArticleId(Long string);

    public Article updateArticle(Article article, UpdateArticleDto updateArticleDto);

    public void deleteArticle(Article article);

    public List<Article> findAllArticle();
}
