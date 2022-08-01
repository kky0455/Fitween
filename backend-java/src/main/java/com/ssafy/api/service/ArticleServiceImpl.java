package com.ssafy.api.service;

import com.ssafy.api.controller.SaveArticleDto;
import com.ssafy.db.entity.Article;
import com.ssafy.db.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
