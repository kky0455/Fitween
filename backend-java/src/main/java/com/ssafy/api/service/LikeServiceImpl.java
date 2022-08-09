package com.ssafy.api.service;

import com.ssafy.common.exception.handler.CustomApiException;
import com.ssafy.db.entity.Article;
import com.ssafy.db.entity.Likes;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.LikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService{

    private final LikesRepository likesRepository;

    @Transactional
    public void likes(User user, Article article) {
        likesRepository.save(Likes.builder()
                .user(user)
                .article(article)
                .build());
    }
    @Transactional
    public void unLikes(User user, Article article) {
        Likes likes = likesRepository.findByUserAndArticle(user, article).orElse(null);
        likesRepository.delete(likes);
    }

    public boolean isLike(User user, Article article) {
        Likes likes = likesRepository.findByUserAndArticle(user, article).orElse(null);
        if (likes == null) {
            return false;
        }
        return true;
    }
}
