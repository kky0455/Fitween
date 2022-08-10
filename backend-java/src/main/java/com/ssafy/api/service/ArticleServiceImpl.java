package com.ssafy.api.service;

import com.ssafy.api.request.SaveArticleDto;
import com.ssafy.api.request.UpdateArticleDto;
import com.ssafy.db.entity.Article;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.ArticleRepository;
import com.ssafy.db.repository.LikesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService{

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    LikesRepository likesRepository;

    @Override
    public void createArticle(SaveArticleDto saveArticleDto, User user) {
        articleRepository.save(Article.builder()
                .title(saveArticleDto.getTitle())
                .feedImg(saveArticleDto.getFeedImg())
                .content(saveArticleDto.getContent())
                .price(saveArticleDto.getPrice())
                .user(user)
                        .likesCount(0)
                .build());
    }
    @Override
    public Article findArticle(Long article_idx) {
        Article article = articleRepository.findById(article_idx).orElse(null);
        return article;
    }

  // 이 어노테이션을 해줘야 업데이트 반영 됨.
    @Override
    public Article updateArticle(Article article, UpdateArticleDto updateArticleDto) {
        article.updateArticle(updateArticleDto);
        return article;
    }


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
