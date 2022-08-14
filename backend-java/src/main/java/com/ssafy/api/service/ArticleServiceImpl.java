package com.ssafy.api.service;

import com.ssafy.api.request.SaveArticleDto;
import com.ssafy.api.request.UpdateArticleDto;
import com.ssafy.common.auth.FWUserDetails;
import com.ssafy.db.entity.Article;
import com.ssafy.db.entity.ArticleImg;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.ArticleImgRepository;
import com.ssafy.db.repository.ArticleRepository;
import com.ssafy.db.repository.LikesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.List;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService{

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    LikesRepository likesRepository;

    @Autowired
    ArticleImgRepository articleImgRepository;

    @Override
    public long createArticle(SaveArticleDto saveArticleDto, User user) {
        Article article = new Article();
        List<String> Imgs = saveArticleDto.getPhotos();
        System.out.println("게시물 생성 1번");
        article = Article.builder().title(saveArticleDto.getTitle())
//                .feedImg(saveArticleDto.getFeedImg())
                .content(saveArticleDto.getContent())
                .price(saveArticleDto.getPrice())
                .user(user)
                .likesCount(0)
                .build();
        articleRepository.save(article);
        System.out.println("게시물 생성 2번" + article);
        Long articleIdx = article.getArticleIdx() ;
//        Article article1 = new Article();
        Article article1 = articleRepository.findById(articleIdx).orElse(null);
        System.out.println("게시물 생성 3번" + article1);
        Imgs.forEach(Img -> {
            System.out.println();
            byte[] decodeImg = Base64.getDecoder().decode(Img);
            System.out.println(decodeImg);
            articleImgRepository.save(ArticleImg.builder().img(decodeImg).article(article1).build());
        });
        System.out.println("저장 테스트");
        return articleIdx;
//        articleRepository.save(Article.builder()
//                .title(saveArticleDto.getTitle())
////                .feedImg(saveArticleDto.getFeedImg())
//                .content(saveArticleDto.getContent())
//                .price(saveArticleDto.getPrice())
//                .user(user)
//                        .likesCount(0)
//                .build());
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
