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
import org.springframework.util.Base64Utils;

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
        List<String> Imgs = saveArticleDto.getPhotos();
        Article article = Article.builder().title(saveArticleDto.getTitle())
                .content(saveArticleDto.getContent())
                .price(saveArticleDto.getPrice())
                .user(user)
                .likesCount(0)
                .build();
        articleRepository.save(article);
        Imgs.forEach(Img -> {
            String baseUrl = Img.substring(0, Img.indexOf("base64,")+7);
//            System.out.println(Img.substring(15, Img.indexOf("base64,")+7));
//            System.out.println(Img.substring(Img.indexOf("base64,")+7, 30));
            String Url = Img.substring(Img.indexOf("base64,")+7);
//            String[] result = Img.split()
//            Img = Img.replace("data:image/png;base64,", "");
            byte[] decodeImg=null;
            try {
                decodeImg = Base64.getDecoder().decode(Url);
            }catch(Exception e){
                System.out.println(e);
            }
            articleImgRepository.save(ArticleImg.builder().article(article).img(decodeImg).baseUrl(baseUrl).build());
        });
        return article.getArticleIdx();
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
