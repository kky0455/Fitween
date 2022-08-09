package com.ssafy.api.service;

import com.ssafy.api.request.SaveArticleDto;
import com.ssafy.api.request.UpdateArticleDto;
import com.ssafy.common.auth.FWUserDetails;
import com.ssafy.db.entity.Article;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.ArticleRepository;
import com.ssafy.db.repository.LikesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService{

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    LikesRepository likesRepository;

    @Override
    public void createArticle(SaveArticleDto saveArticleDto, Authentication authentication) {
        FWUserDetails userDetails = (FWUserDetails) authentication.getDetails();
        User user = userDetails.getUser();
        articleRepository.save(Article.builder()
                .title(saveArticleDto.getTitle())
                .content(saveArticleDto.getContent())
                .price(saveArticleDto.getPrice())
                .user(user)
                .likesCount(0L)
                .build());

    }

    @Override
    public Article findByArticleId(Long article_idx) {
        Article article = articleRepository.findById(article_idx).get();
        return article;
    }

    @Transactional  // 이 어노테이션을 해줘야 업데이트 반영 됨.
    @Override
    public Article updateArticle(Article article, UpdateArticleDto updateArticleDto) {
        article.updateArticle(updateArticleDto.getTitle(), updateArticleDto.getContent(), updateArticleDto.getPrice(), updateArticleDto.isLendstatus());
        return article;
    }

    @Transactional
    @Override
    public void deleteArticle(Article article) {
        articleRepository.delete(article);
    }

    @Override
    public List<Article> findAllArticle() {
        List<Article> articles = articleRepository.findAll();
        return articles;
    }

}
