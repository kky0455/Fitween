package com.ssafy.db.repository;

import com.ssafy.db.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
