package com.ssafy.db.repository;

import com.querydsl.core.types.EntityPath;
import com.querydsl.jpa.JPAExpressions;
import com.ssafy.db.entity.QUser;
import com.ssafy.db.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class UserRepository {

    @PersistenceContext
    private final EntityManager em;
    private JPAExpressions jpaQueryFactory;
    QUser qUser = QUser.user;

    public void save(User user){
        user.setRoles(Collections.singletonList("ROLE_USER")); // 최초 가입 시 USER로 설정
        em.persist(user);
    }

    public boolean existsByUserId(String userId){
        List<User> memberList = em.createQuery("select m from User m where m.userId = :userId", User.class)
                .setParameter("userId", userId)
                .getResultList();
        if(memberList.size() == 0) return false;
        return true;
    }

    public User findById(String userId) throws IllegalStateException {
        List<User> memberList = em.createQuery("select m from User m where m.userId = :userId", User.class)
                .setParameter("userId", userId)
                .getResultList();
        if(memberList.size() == 0) throw new IllegalStateException("해당 유저아이디를 가진 사용자가 없습니다.");
        return memberList.get(0);
    }

    public User findUserByName(String name) {
        User user = jpaQueryFactory.select(qUser).from(qUser)
                .where(qUser.name.eq(name)).fetchOne();
        if(user == null) return null;
        return user;
    }


    public void socialLogin(String email, String refresh){
        User user = findById(email);
        user.changeRefreshToken(refresh);
    }


}
