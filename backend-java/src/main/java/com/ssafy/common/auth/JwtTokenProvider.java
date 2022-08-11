package com.ssafy.common.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ssafy.common.util.JwtTokenUtil;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private static String secretKey = "fitweenSecretadiosfhaiodhfaiodhfiadflkadfklnad,mf";

    //토큰 유효시간 == 1일
    private static final long expireTime = 24*60*60*1000L;

    //리프레시 토큰 유효시간 == 7일
    private final long refreshExpireTime = 7*24*60*60*1000L;

    private final UserDetailsService userDetailsService;

    public static final String ISSUER = "fitween.com";

    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String userPk, List<String> roles) throws UnsupportedEncodingException {
        Claims claims = Jwts.claims().setSubject(userPk); // JWT payLood에 저장되는 정보 단위
        claims.put("roles", roles); // 정보는 key-value 쌍으로 저장
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발생 시간 정보
                .setExpiration(new Date(now.getTime() + expireTime))  // set 토큰 만료 시간
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes("UTF-8"))  // 사용할 암호화 알고리즘과 signature에 들어갈 secret 값 지정
                .compact();
    }

    public static String getToken(String userId) {
        Date expires = JwtTokenUtil.getTokenExpiration(expireTime);
        return JWT.create()
                .withSubject(userId)
                .withExpiresAt(expires)
                .withIssuer(ISSUER)
                .withIssuedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                .sign(Algorithm.HMAC512(secretKey.getBytes()));
    }

    //jwt 인증 정보 조회
    public Authentication getAuthentication(String token){
        UserDetails userDetails =userDetailsService.loadUserByUsername(this.getUserPk(token));

        return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
    }

    public String getUserPk(String token){
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
    }

    // Request의 Header에서 token 값을 가져옴 "X-AUTH-TOKEN" : "TOKEN"
    public String resolveToken(HttpServletRequest request){
        return request.getHeader("X-AUTH-TOKEN");
    }


    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken){
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException e){
            e.printStackTrace();
            return false;
        } catch (Exception e){
            return false;
        }
    }

    // JWT refresh 토큰 생성
    public String createRefreshToken(String userId, List<String> roles) throws UnsupportedEncodingException {
        Claims claims = Jwts.claims().setSubject(userId); // JWT payLood에 저장되는 정보 단위
        claims.put("roles", roles); // 정보는 key-value 쌍으로 저장
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshExpireTime))
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes("UTF-8"))
                .compact();
    }


}
