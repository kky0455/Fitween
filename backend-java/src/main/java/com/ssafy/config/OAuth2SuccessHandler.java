package com.ssafy.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.api.controller.UserRequestMapper;
import com.ssafy.api.service.UserService;
import com.ssafy.common.auth.JwtTokenProvider;
import com.ssafy.db.dto.UserDto;
import com.ssafy.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRequestMapper userRequestMapper;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
        throws IOException, ServletException{

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        UserDto user = userRequestMapper.toDto(oAuth2User);

        if(!userRepository.existsByUserId(user.getUserId())){
            userService.joinSocial(user);
        }

        List<String> roles = new ArrayList<>();
        roles.add("ROLE_USER");
        String accessToken = jwtTokenProvider.createToken(user.getEmail(), roles);
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getEmail(), roles);

        userService.socialLogin(user.getEmail(),refreshToken);

        log.info("email : {}", user.getEmail());
        log.info("name : {}", user.getName());
        log.info("access token : {}", accessToken);
        log.info("refresh token : {}", refreshToken);
        String targetUrl;

        response.setContentType("text/html;charset=UTF-8");
        response.addHeader("accessToken", accessToken);
        response.setContentType("application/json;charset=UTF-8");
        targetUrl = UriComponentsBuilder.fromUriString("/home")
                .queryParam("accessToken", accessToken)
                .build().toUriString();
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

}
