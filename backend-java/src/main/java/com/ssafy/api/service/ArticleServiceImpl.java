package com.ssafy.api.service;

import com.ssafy.api.request.ArticleImgDto;
import com.ssafy.api.request.ArticleRecommendDto;
import com.ssafy.api.request.SaveArticleDto;
import com.ssafy.api.request.UpdateArticleDto;
import com.ssafy.db.entity.*;
import com.ssafy.db.repository.ArticleImgRepository;
import com.ssafy.db.repository.ArticleRepository;
import com.ssafy.db.repository.LikesRepository;
import com.ssafy.db.repository.UserRepository2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService{

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    LikesRepository likesRepository;

    @Autowired
    ArticleImgRepository articleImgRepository;

    @Autowired
    UserRepository2 userRepository2;

    @Override
    public long createArticle(SaveArticleDto saveArticleDto, User user) {
        List<String> Imgs = saveArticleDto.getPhotos();
        Article article = Article.builder().title(saveArticleDto.getTitle())
                .content(saveArticleDto.getContent())
                .price(saveArticleDto.getPrice())
                .user(user)
                .likesCount(0)
                .category(saveArticleDto.getCategory())
                .build();
        articleRepository.save(article);
        Imgs.forEach(Img -> {
            String baseUrl = Img.substring(0, Img.indexOf("base64,")+7);
            String Url = Img.substring(Img.indexOf("base64,")+7);
            byte[] decodeImg=null;
            try {
                decodeImg = Base64.getDecoder().decode(Url);
            }catch(Exception e){
                System.out.println(e);
            }
            articleImgRepository.save(ArticleImg.builder().article(article).img(decodeImg).baseUrl(baseUrl).build());
        });
        return article.getArticleIdx();
    }
    @Override
    public Article findArticle(Long article_idx) {
        Article article = articleRepository.findById(article_idx).orElse(null);
        return article;
    }

    // 이 어노테이션을 해줘야 업데이트 반영 됨.
    @Override
    @Transactional
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

    @Override
    public List<ArticleRecommendDto> findAllTest(Category categoryCode, User currentUser) {
        List<ArticleRecommendDto> articleRecommendDtoList = new ArrayList<>();
        List<User> users = userRepository2.findAll();
        String[] region = currentUser.getRegion().split(" ");
        if (categoryCode == Category.all) {
            users.forEach(user -> {
                String[] userRegion = user.getRegion().split(" ");
                if (!Objects.equals(region[1], userRegion[1])) {
                    System.out.println(region[1] + userRegion[1]);
                    return;
                }
                boolean clothTF = false;
                boolean shoesTF = false;
                double diffHeight = user.getHeight() - currentUser.getHeight();
                double diffWeight = user.getWeight() - currentUser.getWeight();
                if ((Math.abs(diffHeight) < 10) && (Math.abs(diffWeight)) < 10) {
                    clothTF = true;
                }
                if (user.getFootSize() == currentUser.getFootSize()) {
                    shoesTF = true;
                }
                List<Article> articleList = user.getArticles();
                boolean finalClothTF = clothTF;
                boolean finalShoesTF = shoesTF;
                articleList.forEach(article -> {
                    boolean likeStatus;
                    Likes likes = likesRepository.findByUserAndArticle(currentUser, article).orElse(null);
                    if (likes == null) {
                        likeStatus = false;
                    } else {
                        likeStatus = true;
                    }
                    if ((article.getCategory() != Category.shoes) && finalClothTF == true) {
                        List<Object> Imgs = new ArrayList<>();
                        if (article.getArticleImgs().size() != 0) {
                            ArticleImgDto articleImgDto = new ArticleImgDto(article.getArticleImgs().get(0).getBaseUrl(), article.getArticleImgs().get(0).getImg());
                            Imgs.add(articleImgDto);
                        }
                        articleRecommendDtoList.add(ArticleRecommendDto.builder()
                                .articleIdx(article.getArticleIdx())
                                .title(article.getTitle())
                                .price(article.getPrice())
                                .nickname(user.getNickname())
                                .lendStatus(article.isLendStatus())
                                .likeStatus(likeStatus)
                                .likesCount(article.getLikes().size())
                                .userId(user.getUserId())
                                .feedArticleImg(Imgs)
                                .build());
                    }
                    if ((article.getCategory() == Category.shoes) && finalShoesTF == true) {
                        List<Object> Imgs = new ArrayList<>();
                        if (article.getArticleImgs().size() != 0) {
                            ArticleImgDto articleImgDto = new ArticleImgDto(article.getArticleImgs().get(0).getBaseUrl(), article.getArticleImgs().get(0).getImg());
                            Imgs.add(articleImgDto);
                        }
                        articleRecommendDtoList.add(ArticleRecommendDto.builder()
                                .articleIdx(article.getArticleIdx())
                                .title(article.getTitle())
                                .price(article.getPrice())
                                .nickname(user.getNickname())
                                .lendStatus(article.isLendStatus())
                                .likeStatus(likeStatus)
                                .likesCount(article.getLikes().size())
                                .userId(user.getUserId())
                                .feedArticleImg(Imgs)
                                .build());
                    }
                });
            });

        } else {
            users.forEach(user -> {
                String[] userRegion = user.getRegion().split(" ");
                if (!Objects.equals(region[1], userRegion[1])) {
                    return;
                }
                boolean clothTF = false;
                boolean shoesTF = false;
                double diffHeight = user.getHeight() - currentUser.getHeight();
                double diffWeight = user.getWeight() - currentUser.getWeight();
                if ((Math.abs(diffHeight) < 10) && (Math.abs(diffWeight)) < 10) {
                    clothTF = true;
                }
                if (user.getFootSize() == currentUser.getFootSize()) {
                    shoesTF = true;
                }
                List<Article> articleList = user.getArticles();
                boolean finalClothTF = clothTF;
                boolean finalShoesTF = shoesTF;
                articleList.forEach(article -> {
                    boolean likeStatus;
                    Likes likes = likesRepository.findByUserAndArticle(currentUser, article).orElse(null);
                    if (likes == null) {
                        likeStatus = false;
                    } else {
                        likeStatus = true;
                    }
                    if ((article.getCategory() == categoryCode) && categoryCode != Category.shoes && finalClothTF == true) {
                        List<Object> Imgs = new ArrayList<>();
                        if (article.getArticleImgs().size() != 0) {
                            ArticleImgDto articleImgDto = new ArticleImgDto(article.getArticleImgs().get(0).getBaseUrl(), article.getArticleImgs().get(0).getImg());
                            Imgs.add(articleImgDto);
                        }
                        articleRecommendDtoList.add(ArticleRecommendDto.builder()
                                .articleIdx(article.getArticleIdx())
                                .title(article.getTitle())
                                .price(article.getPrice())
                                .nickname(user.getNickname())
                                .lendStatus(article.isLendStatus())
                                .likeStatus(likeStatus)
                                .likesCount(article.getLikes().size())
                                .userId(user.getUserId())
                                .feedArticleImg(Imgs)
                                .build());
                    }
                    if ((article.getCategory() == categoryCode) && categoryCode == Category.shoes && finalShoesTF == true) {
                        List<Object> Imgs = new ArrayList<>();
                        if (article.getArticleImgs().size() != 0) {
                            ArticleImgDto articleImgDto = new ArticleImgDto(article.getArticleImgs().get(0).getBaseUrl(), article.getArticleImgs().get(0).getImg());
                            Imgs.add(articleImgDto);
                        }
                        articleRecommendDtoList.add(ArticleRecommendDto.builder()
                                .articleIdx(article.getArticleIdx())
                                .title(article.getTitle())
                                .price(article.getPrice())
                                .nickname(user.getNickname())
                                .lendStatus(article.isLendStatus())
                                .likeStatus(likeStatus)
                                .likesCount(article.getLikes().size())
                                .userId(user.getUserId())
                                .feedArticleImg(Imgs)
                                .build());
                    }
                });
            });
        }
        return articleRecommendDtoList;
    }
}
