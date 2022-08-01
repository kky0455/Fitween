package com.ssafy.api.service;

import com.ssafy.api.controller.SaveArticleDto;
import com.ssafy.db.entity.Article;

public interface ArticleService {

    public Article createArticle(SaveArticleDto saveBoardDto);
}
