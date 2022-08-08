package com.ssafy.db.repository;

import com.ssafy.db.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    Follow findFollowByFromUserIdAndToUserId(String from_user_id, String to_user_id);

//    Optional<Follow> findFollowByFromAndTo(String from_user_id, String to_user_id);

    @Query(value = "SELECT COUNT(*) FROM follow WHERE to_user_id = :to_user_id", nativeQuery = true)
    int findFollowerCountById(@Param("to_user_id") Long to_user_id);

    @Query(value = "SELECT COUNT(*) FROM follow WHERE from_user_id = :from_user_id and to_user_id = :to_user_id", nativeQuery = true)
    int findFollowByToAndFrom(@Param("to_user_id") Long to_user_id, @Param("from_user_id") Long from_user_id);

    @Query(value = "SELECT COUNT(*) FROM follow WHERE from_user_id = :from_user_id", nativeQuery = true)
    int findFollowingCountById(@Param("from_user_id") Long from_user_id);

    @Modifying
    @Query(value = "INSERT INTO follow(from_user_id, to_user_id) VALUES(:from_user_id, :to_user_id)", nativeQuery = true)
    void follow(String from_user_id, String to_userid);

    @Modifying
    @Query(value = "DELETE FROM follow WHERE from_user_id = :from_id AND to_user_id = :to_id", nativeQuery = true)
    void unFollow(Long from_id, Long to_id);

//    public List<Follow> findByFrom(User from);
//    public List<Follow> findByTo(User to);
//
    @Query("select f from Follow f where f.from.userIdx = :userIdx and f.to.userIdx = :targetUserIdx")
    public Optional<Follow> findByUserIdAndTargetUserId(@Param("userIdx") Long userIdx, @Param("targetUserIdx") Long targetUserIdx);
//
//    Optional<Follow> findByFromAndTo(User from, User to);
//
//    Long countByFrom(User from);
//    Long countByTo(User to);
}
