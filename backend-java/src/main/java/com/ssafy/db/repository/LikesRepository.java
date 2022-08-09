package com.ssafy.db.repository;

import com.ssafy.db.entity.Article;
import com.ssafy.db.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {

    void deleteLikesByArticle(Article article);

    @Modifying
    @Query(value = "INSERT INTO likes(article_idx, user_idx) VALUES(:articleIdx, :userIdx)", nativeQuery = true)
    void likes(Long articleIdx, Long userIdx);

    @Modifying
    @Query(value = "DELETE FROM likes WHERE article_idx = :articleId AND user_idx = :userIdx", nativeQuery = true)
    void unLikes(Long articleIdx, Long userIdx);

}
