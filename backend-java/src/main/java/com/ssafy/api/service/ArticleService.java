package com.ssafy.api.service;

import com.ssafy.api.request.ArticleLikeDto;
import com.ssafy.api.request.ArticleRecommendDto;
import com.ssafy.api.request.SaveArticleDto;
import com.ssafy.api.request.UpdateArticleDto;
import com.ssafy.db.entity.Article;
import com.ssafy.db.entity.Category;
import com.ssafy.db.entity.User;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ArticleService {

    public long createArticle(SaveArticleDto saveBoardDto, User user);

    public Article findArticle(Long article_idx);

    public Article updateArticle(Article article, UpdateArticleDto updateArticleDto);

    public void deleteArticle(Article article);

    public List<Article> findAllArticle();

    public List<ArticleRecommendDto> findAllTest(Category categotyCode, User user);

    public List<ArticleLikeDto> findLikeArticle(Category category, User user);
}
