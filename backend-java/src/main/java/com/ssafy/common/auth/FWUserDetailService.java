package com.ssafy.common.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.ssafy.api.service.UserService;
import com.ssafy.db.entity.User;
import org.springframework.stereotype.Service;


/**
 * 현재 액세스 토큰으로 부터 인증된 유저의 상세정보(활성화 여부, 만료, 롤 등) 관련 서비스 정의.
 */
@Component
@Service
@RequiredArgsConstructor
public class FWUserDetailService implements UserDetailsService{
	@Autowired
	UserService userService;


	// db에서 유저의 정보를 조회(loadUserByUsername)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    		User user = userService.getUserByUserId(username);
    		if(user != null) {
    			FWUserDetails userDetails = new FWUserDetails(user);
    			return userDetails;
    		}
    		return null;
    }
}
