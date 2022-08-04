package com.ssafy.db.repository;

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

    public void save(User user){
        user.setRoles(Collections.singletonList("ROLE_USER")); // 최초 가입 시 USER로 설정
        em.persist(user);
    }

    public boolean existsByEmail(String email){
        List<User> memberList = em.createQuery("select m from User m where m.email = :email", User.class)
                .setParameter("email", email)
                .getResultList();
        if(memberList.size() == 0) return false;
        return true;
    }

    public User findByEmail(String email) throws IllegalStateException {
        List<User> memberList = em.createQuery("select m from User m where m.email = :email", User.class)
                .setParameter("email", email)
                .getResultList();
        if(memberList.size() == 0) throw new IllegalStateException("해당 이메일을 가진 사용자가 없습니다.");
        return memberList.get(0);
    }


    public void socialLogin(String email, String refresh){
        User user = findByEmail(email);
        user.changeRefreshToken(refresh);
    }


}
