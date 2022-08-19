package com.ssafy.db.repository;

import com.ssafy.db.entity.Follow;
import com.ssafy.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    List<Follow> findAllByFrom(User from);

    int countByFrom(User from);

    int countByTo(User to);

    Optional<Follow> findByFromAndTo(User from, User to);
}
