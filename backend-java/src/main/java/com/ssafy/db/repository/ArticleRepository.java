package com.ssafy.db.repository;

import com.ssafy.db.entity.Article;
import com.ssafy.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Query("select a from Article a where a.user=:user order by a.articleIdx desc ")
    Optional<List<Article>> findAllByUser(User user);
}
