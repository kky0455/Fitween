package com.ssafy.api.service;

import com.ssafy.api.controller.SaveArticleDto;
import com.ssafy.db.entity.Article;
import com.ssafy.db.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArticleServiceImpl implements ArticleService{

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public Article createArticle(SaveArticleDto saveArticleDto) {
        System.out.println("들어옴?");
        Article article = articleRepository.save(saveArticleDto.toEntity());
        return article;
    }

    @Override
    public Article findByArticleId(Long article_idx) {
        Article article = articleRepository.findById(article_idx).get();
        return article;
    }

    @Transactional  // 이 어노테이션을 해줘야 업데이트 반영 됨.
    @Override
    public Article updateArticle(Article article, SaveArticleDto saveArticleDto) {
        System.out.println("찍히나?"+saveArticleDto.toString());
        article.updateArticle(saveArticleDto);
        return article;
    }

    @Transactional
    @Override
    public void deleteArticle(Article article) {
        articleRepository.delete(article);
    }
}
