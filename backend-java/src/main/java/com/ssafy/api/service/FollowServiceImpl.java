package com.ssafy.api.service;

import com.ssafy.api.request.FollowDto;
import com.ssafy.common.exception.handler.CustomApiException;
import com.ssafy.common.exception.handler.UserNotFoundException;
import com.ssafy.db.entity.Follow;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.FollowRepository;
import com.ssafy.db.repository.UserRepositorySupport;
import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService{

    @Autowired
    private final FollowRepository followRepository;

    @Autowired
    private final UserRepositorySupport userRepositorySupport;

    private final EntityManager em;

    @Transactional
    public void follow(String fromUserId, String toUserId) {
        if(followRepository.findFollowByFromUserIdAndToUserId(fromUserId, toUserId) != null) throw new CustomApiException("이미 팔로우 하였습니다.");
        User from = userRepositorySupport.findUserByUserId(fromUserId).get();
        User to = userRepositorySupport.findUserByUserId(toUserId).get();
        Follow follow = new Follow();
        follow.setFrom(from);
        follow.setTo(to);
        followRepository.save(follow);
    }

    @Transactional
    public void unFollow(Long fromUserId, Long toUserId) {
        Follow follow = followRepository.findByUserIdAndTargetUserId(fromUserId, toUserId).orElseThrow(UserNotFoundException::new);
        followRepository.delete(follow);
    }

    @Transactional
    public List<FollowDto> getFollower(String profileId, String loginId) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT u.id, u.name, u.profile_img_url, ");
        sb.append("if ((SELECT 1 FROM follow WHERE from_user_id = ? AND to_user_id = u.id), TRUE, FALSE) AS followState, ");
        sb.append("if ((?=u.id), TRUE, FALSE) AS loginUser ");
        sb.append("FROM user u, follow f ");
        sb.append("WHERE u.id = f.from_user_id AND f.to_user_id = ?");
        // 쿼리 완성
        Query query = em.createNativeQuery(sb.toString())
                .setParameter(1, loginId)
                .setParameter(2, loginId)
                .setParameter(3, profileId);

        //JPA 쿼리 매핑 - DTO에 매핑
        JpaResultMapper result = new JpaResultMapper();
        List<FollowDto> followDtoList = result.list(query, FollowDto.class);
        return followDtoList;
    }

    @Transactional
    public List<FollowDto> getFollowing(String profileId, String loginId) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT u.id, u.name, u.profile_img_url, ");
        sb.append("if ((SELECT 1 FROM follow WHERE from_user_id = ? AND to_user_id = u.id), TRUE, FALSE) AS followState, ");
        sb.append("if ((?=u.id), TRUE, FALSE) AS loginUser ");
        sb.append("FROM user u, follow f ");
        sb.append("WHERE u.id = f.to_user_id AND f.from_user_id = ?");

        // 쿼리 완성
        Query query = em.createNativeQuery(sb.toString())
                .setParameter(1, loginId)
                .setParameter(2, loginId)
                .setParameter(3, profileId);

        //JPA 쿼리 매핑 - DTO에 매핑
        JpaResultMapper result = new JpaResultMapper();
        List<FollowDto> followDtoList = result.list(query, FollowDto.class);
        return followDtoList;
    }


//    @Autowired
//    private final UserRepository userRepository;
//
//    public User getUser(String userId) {
//        User user = userRepository.findByUserId(userId).orElseThrow(UserNotFoundException :: new);
//        return user;
//    }

//    @Override
//    public Long follow(String fromUserId, String toUserId){
//        User from = getUser(fromUserId);
//        User to = getUser(toUserId);
//
//        followRepository.findByFromAndTo(from, to).ifPresent(
//                msg -> {
//                    throw new IllegalStateException("이미 팔로우중인 회원입니다.");
//                }
//        );
//
//        Follow follow = new Follow();
//        follow.setFrom(from);
//        follow.setTo(to);
//
//        followRepository.save(follow);
//
//        return follow.getId();
//    }
//
//    @Override
//    public void unfollow(String fromUserId, String toUserId){
//        Follow follow = followRepository.findByUserIdAndTargetUserId(fromUserId, toUserId).orElseThrow(UserNotFoundException::new);
//        followRepository.delete(follow);
//    }
//
//    @Override
//    public int getSizeofFollowers(String userId){
//        User user = getUser(userId);
//        return followRepository.findByTo(user).size();
//    }
//
//    @Override
//    public int getSizeofFollowings(String userId){
//        User user = getUser(userId);
//        return followRepository.findByFrom(user).size();
//    }
}
