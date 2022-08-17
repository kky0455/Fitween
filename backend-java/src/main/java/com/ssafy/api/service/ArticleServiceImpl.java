package com.ssafy.api.service;

import com.ssafy.api.request.*;
import com.ssafy.db.entity.*;
import com.ssafy.db.repository.ArticleImgRepository;
import com.ssafy.db.repository.ArticleRepository;
import com.ssafy.db.repository.LikesRepository;
import com.ssafy.db.repository.UserRepository2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
        // 리턴할 배열 생성
        List<ArticleRecommendDto> articleRecommendDtoList = new ArrayList<>();
        // 전체 사용자 정보 가져오기
//        Sort.by(Sort.Direction.DESC, "createTime")
        List<User> users = userRepository2.findAll();
        // 지역 비교용 리스트 생성
        String[] region = currentUser.getRegion().split(" ");
        // 카테고리가 all일 때,
        if (categoryCode == Category.all) {
            // 유저 리스트에 있는 유저 목록 탐색
            users.forEach(user -> {
                // 탐색한 유저와 내 지역이 일치하지 않으면 컨티뉴
                String[] userRegion = user.getRegion().split(" ");
                if (!Objects.equals(region[1], userRegion[1])) {
                    System.out.println(region[1] + userRegion[1]);
                    return;
                }
                // 의류와 신발 TF
                boolean clothTF = false;
                boolean shoesTF = false;
                // 신체사이즈 차이 기록
                double diffHeight = user.getHeight() - currentUser.getHeight();
                double diffWeight = user.getWeight() - currentUser.getWeight();
                // 키와 몸무게 차이가 둘 다 10 이내인 경우
                if ((Math.abs(diffHeight) < 10) && (Math.abs(diffWeight)) < 10) {
                    clothTF = true;
                }
                // 발 사이즈가 똑같은 경우
                if (user.getFootSize() == currentUser.getFootSize()) {
                    shoesTF = true;
                }
                // 해당 유저가 작성한 게시물 목록 탐색
                List<Article> articleList = user.getArticles();
                boolean finalClothTF = clothTF;
                boolean finalShoesTF = shoesTF;
                // 목록 탐색
                articleList.forEach(article -> {
                    // 좋아요 여부 판단용 likeStatus
                    boolean likeStatus;
                    Likes likes = likesRepository.findByUserAndArticle(currentUser, article).orElse(null);
                    if (likes == null) {
                        likeStatus = false;
                    } else {
                        likeStatus = true;
                    }
                    // 게시물 카테고리가 신발이 아니고 신체 사이즈 비슷할 경우
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

    @Override
    public List<ArticleLikeDto> findLikeArticle(Category categoryCode, User currentUser) {
        // 반환용 리스트
        List<ArticleLikeDto> articleLikeDtoList = new ArrayList<>();
        // 지역정보 저장
        String[] region = currentUser.getRegion().split(" ");
        // 좋아요한 게시물 리스트 불러오기
        List<Likes> likesList = likesRepository.findAllByUser(currentUser).orElse(null);
        // 카테고리 코드가 all인 경우
        if (categoryCode == Category.all) {
            // 좋아요 목록 탐색
            likesList.forEach(likes -> {
                Article article = likes.getArticle();
                User user = article.getUser();
                String[] userRegion = user.getRegion().split(" ");
                if (!Objects.equals(region[1], userRegion[1])) {
                    return;
                }
                boolean clothTF = false;
                boolean shoesTF = false;
                double diffHeight = user.getHeight() - currentUser.getHeight();
                double diffWeight = user.getWeight() - currentUser.getWeight();
                if ( (Math.abs(diffHeight) < 10) && (Math.abs(diffWeight)) < 10 ) {
                    clothTF = true;
                }
                if (user.getFootSize() == currentUser.getFootSize()) {
                    shoesTF = true;
                }
                boolean finalClothTF = clothTF;
                boolean finalShoesTF = shoesTF;
                if (categoryCode != Category.shoes && finalClothTF == true) {
                    List<Object> Imgs = new ArrayList<>();
                    if (article.getArticleImgs().size() != 0) {
                        ArticleImgDto articleImgDto = new ArticleImgDto(article.getArticleImgs().get(0).getBaseUrl(), article.getArticleImgs().get(0).getImg());
                        Imgs.add(articleImgDto);
                    }
                    articleLikeDtoList.add(ArticleLikeDto.builder()
                            .articleIdx(article.getArticleIdx())
                            .title(article.getTitle())
                            .price(article.getPrice())
                            .nickname(user.getNickname())
                            .category(article.getCategory())
                            .lendStatus(article.isLendStatus())
                            .likeStatus(true)
                            .likesCount(article.getLikes().size())
                            .userId(user.getUserId())
                            .feedArticleImg(Imgs)
                            .build());
                }
                if (article.getCategory() == Category.shoes && finalShoesTF == true) {
                    List<Object> Imgs = new ArrayList<>();
                    if (article.getArticleImgs().size() != 0) {
                        ArticleImgDto articleImgDto = new ArticleImgDto(article.getArticleImgs().get(0).getBaseUrl(), article.getArticleImgs().get(0).getImg());
                        Imgs.add(articleImgDto);
                    }
                    articleLikeDtoList.add(ArticleLikeDto.builder()
                            .articleIdx(article.getArticleIdx())
                            .title(article.getTitle())
                            .price(article.getPrice())
                            .nickname(user.getNickname())
                            .category(article.getCategory())
                            .lendStatus(article.isLendStatus())
                            .likeStatus(true)
                            .likesCount(article.getLikes().size())
                            .userId(user.getUserId())
                            .feedArticleImg(Imgs)
                            .build());
                }
            });
        }
        else {
            likesList.forEach(likes -> {
                Article article = likes.getArticle();
                User user = article.getUser();
                String[] userRegion = user.getRegion().split(" ");
                if (!Objects.equals(region[1], userRegion[1])) {
                    return;
                }
                boolean clothTF = false;
                boolean shoesTF = false;
                double diffHeight = user.getHeight() - currentUser.getHeight();
                double diffWeight = user.getWeight() - currentUser.getWeight();
                if ( (Math.abs(diffHeight) < 10) && (Math.abs(diffWeight)) < 10 ) {
                    clothTF = true;
                }
                if (user.getFootSize() == currentUser.getFootSize()) {
                    shoesTF = true;
                }
                boolean finalClothTF = clothTF;
                boolean finalShoesTF = shoesTF;
                if ((article.getCategory() == categoryCode) && categoryCode != Category.shoes && finalClothTF == true) {
                    List<Object> Imgs = new ArrayList<>();
                    if (article.getArticleImgs().size() != 0) {
                        ArticleImgDto articleImgDto = new ArticleImgDto(article.getArticleImgs().get(0).getBaseUrl(), article.getArticleImgs().get(0).getImg());
                        Imgs.add(articleImgDto);
                    }
                    articleLikeDtoList.add(ArticleLikeDto.builder()
                            .articleIdx(article.getArticleIdx())
                            .title(article.getTitle())
                            .price(article.getPrice())
                            .nickname(user.getNickname())
                            .category(article.getCategory())
                            .lendStatus(article.isLendStatus())
                            .likeStatus(true)
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
                    articleLikeDtoList.add(ArticleLikeDto.builder()
                            .articleIdx(article.getArticleIdx())
                            .title(article.getTitle())
                            .price(article.getPrice())
                            .nickname(user.getNickname())
                            .category(article.getCategory())
                            .lendStatus(article.isLendStatus())
                            .likeStatus(true)
                            .likesCount(article.getLikes().size())
                            .userId(user.getUserId())
                            .feedArticleImg(Imgs)
                            .build());
                }
            });
        }

        return articleLikeDtoList;
    }
}
