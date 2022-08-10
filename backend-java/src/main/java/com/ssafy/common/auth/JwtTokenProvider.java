package com.ssafy.common.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ssafy.api.service.UserService;
import com.ssafy.common.util.JwtTokenUtil;
import com.ssafy.db.entity.User;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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

    //토큰 유효시간 == 10분
    private static final long expireTime = 10*60*1000L;

    //리프레시 토큰 유효시간 == 1시간
    private final long refreshExpireTime = 24*60*60*1000L;

    private final UserDetailsService userDetailsService;

    public static final String ISSUER = "fitween.com";

    public UserService userService;

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
    @Transactional
    //jwt 인증 정보 조회
    public Authentication getAuthentication(String token){
        System.out.println("여기 오냐!");
        if (token != null) {
            // parse the token and validate it (decode)
            JWTVerifier verifier = JwtTokenUtil.getVerifier();
            JwtTokenUtil.handleError(token);
            DecodedJWT decodedJWT = verifier.verify(token.replace(JwtTokenUtil.TOKEN_PREFIX, ""));
            String userId = decodedJWT.getSubject();
            System.out.println("여기 오냐?");
            if (userId != null) {
                // jwt 토큰에 포함된 계정 정보(userId) 통해 실제 디비에 해당 정보의 계정이 있는지 조회.
                User user = userService.getUserByUserId(userId);
                if (user != null) {
                    // 식별된 정상 유저인 경우, 요청 context 내에서 참조 가능한 인증 정보(jwtAuthentication) 생성.
                    FWUserDetails userDetails = new FWUserDetails(user);
                    UsernamePasswordAuthenticationToken jwtAuthentication = new UsernamePasswordAuthenticationToken(userId,
                            null, userDetails.getAuthorities());
                    jwtAuthentication.setDetails(userDetails);
                    Authentication authentication = jwtAuthentication;
                }
            }
        }

        UserDetails userDetails =userDetailsService.loadUserByUsername(this.getUserPk(token));


        return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
    }

    public String getUserPk(String token){
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
    }

    // Request의 Header에서 token 값을 가져옴 "X-AUTH-TOKEN" : "TOKEN"
    public String resolveToken(HttpServletRequest request){
        System.out.println(request);
        return request.getHeader("Authorization");
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
