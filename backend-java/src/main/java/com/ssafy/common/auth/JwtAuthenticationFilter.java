package com.ssafy.common.auth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.ssafy.api.service.UserService;
import com.ssafy.common.util.ResponseBodyWriteUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

/**
 * 요청 헤더에 jwt 토큰이 있는 경우, 토큰 검증 및 인증 처리 로직 정의.
 */
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        // 헤더에서 JWT 를 받아옴
//        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
//        System.out.println("토큰 테스트");
//        System.out.println("asdasd"+token);
        String token = ((HttpServletRequest) request).getHeader("authorization");
        System.out.println("dofilter토큰" + token);

        // 유효한 토큰인지 확인
//        if(token != null && jwtTokenProvider.validateToken(token)){
//            // 토큰이 유효하면 토큰으로 부터 유저의 정보를 반환
//            System.out.println("저장에 성공했습니다.");
//            Authentication authentication = jwtTokenProvider.getAuthentication(token);
//            // SecurityContext에 Authentication 객체를 저장
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }
        try {
            // If header is present, try grab user principal from database and perform authorization
            System.out.println("0");

//            Authentication authentication = jwtTokenProvider.getAuthentication("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMiIsImlzcyI6InNzYWZ5LmNvbSIsImV4cCI6MTY2MTQ5NzkyNiwiaWF0IjoxNjYwMjAxOTI2fQ.9uRA6WKEXXHnRRlq_QR_tn0ti1vtgFnZk9LjoW68kZ_G_RySmKB7Bfi22Gq4wvpT0xtKwkVuleB0ULfyGNfpDA");
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            System.out.println("1번");
            // jwt 토큰으로 부터 획득한 인증 정보(authentication) 설정.
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("2");
        } catch (Exception ex) {
            System.out.println("실패");

            }
        chain.doFilter(request, response);
    }
}