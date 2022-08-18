package com.ssafy.db.repository;

import com.ssafy.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

/**
 * 유저 모델 관련 디비 쿼리 생성을 위한 JPA Query Method 인터페이스 정의.
 */
@Repository
public interface UserRepository2 extends JpaRepository<User, Long> {

    Optional<User> findByUserIdx(Long userIdx);
    Optional<User> findUserByUserId(String userId);
    Optional<User> findUserByNickname(String nickname);
    @Query("select u from User u where abs(u.height - :Height) < 10 and abs(u.weight - :Weight) < 10")
    Optional<List<User>> findAllCloth(double Height, double Weight);
    @Query("select u from User u where u.footSize=:footsize")
    Optional<List<User>> findAllShoes(int footsize);
}