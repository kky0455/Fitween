package com.ssafy.config;

import com.ssafy.api.service.CustomOauth2UserService;
import com.ssafy.api.service.UserService;
import com.ssafy.common.auth.FWUserDetailService;
import com.ssafy.common.auth.JwtAuthenticationFilter;
import com.ssafy.common.auth.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 인증(authentication) 와 인가(authorization) 처리를 위한 스프링 시큐리티 설정 정의.
 */

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근하면 권한 및 인증을 미리 체크
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomOauth2UserService oAuth2UserService;
    private final OAuth2SuccessHandler successHandler;

//    private static final String[] AUTH_WHITELIST = {
//            "/authenticate",
//            "/swagger-resources/**",
//            "/swagger-ui/**",
//            "/v3/api-docs",
//            "/webjars/**"
//    };

    // 암호화에 필요한 PasswordEncoder를 Bean으로 등록
    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // authenticationManager를 Bean으로 등록
    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()   // rest api만을 고려해 기본 설정은 해제
                .csrf().disable()  // csrf 보안 토큰 disable 처리
                .headers()
                .frameOptions()
                .deny()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // 토큰 기반 인증이므로 세션 역시 사용하지 않음
                .and()
                .authorizeRequests()  // 요청에 대한 사용 권한 체크
                //.antMatchers(AUTH_WHITELIST).permitAll()
                .antMatchers("/api/**").authenticated()
                .antMatchers("/user/**").hasRole("USER")
                .anyRequest().permitAll()  // 그 외 나머지 요청은 누구나 접근 가능
                .and()
                .cors()
                .and() /* OAuth */
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class)
                .oauth2Login()
                .successHandler(successHandler)
                .userInfoEndpoint() // OAuth2 로그인 성공 후에 가져올 설정들
                .userService(oAuth2UserService); // 서버에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능 명시

        http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                UsernamePasswordAuthenticationFilter.class);

    }
}