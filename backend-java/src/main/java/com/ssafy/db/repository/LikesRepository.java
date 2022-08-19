package com.ssafy.db.repository;

import com.ssafy.db.entity.Article;
import com.ssafy.db.entity.Likes;
import com.ssafy.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {

    @Query("SELECT p FROM Likes p ORDER BY p.article.articleIdx DESC ")
    Optional<List<Likes>> findAllByUser(User user);


    Optional<Likes> findByUserAndArticle(User user, Article article);


}
