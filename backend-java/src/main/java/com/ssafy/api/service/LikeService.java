package com.ssafy.api.service;

import com.ssafy.db.entity.Article;
import com.ssafy.db.entity.User;

public interface LikeService {
    public void likes(User user, Article article);

    public void unLikes(User user, Article article);

    public boolean isLike(User user, Article article);
}
