package com.ssafy.common.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ssafy.api.service.UserService;
import com.ssafy.common.util.JwtTokenUtil;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.UserRepository2;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
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
    public final UserRepository2 userRepository2;

//    @PostConstruct
//    protected void init(){
//        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
//    }

    public String createToken(String userPk, List<String> roles) throws UnsupportedEncodingException {
        Claims claims = Jwts.claims().setSubject(userPk); // JWT payLood에 저장되는 정보 단위
        claims.put("roles", roles); // 정보는 key-value 쌍으로 저장
        Date now = new Date();
//
//        return Jwts.builder()
//                .setClaims(claims) // 정보 저장
//                .setIssuedAt(now) // 토큰 발생 시간 정보
//                .setExpiration(new Date(now.getTime() + expireTime))  // set 토큰 만료 시간
//                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes("UTF-8"))  // 사용할 암호화 알고리즘과 signature에 들어갈 secret 값 지정
//                .compact();
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;
        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(secretKey);
        Key signatureKey =
                new SecretKeySpec(
                        secretKeyBytes
                        ,signatureAlgorithm.getJcaName()
                );
//        return Jwts.builder()
//                .setClaims(claims)
////                .setSubject(userDto.getId().toString())
//                .signWith(signatureKey,signatureAlgorithm)
//                .setExpiration(new Date(now.getTime() + expireTime))
//                .compact();
//
//    }
        System.out.println(ISSUER);
        System.out.println(secretKey.getBytes().toString());
        System.out.println(userPk);
        return JWT.create()
                .withSubject(userPk)
                .withExpiresAt(new Date(now.getTime() + expireTime))
                .withIssuer(ISSUER)
                .withIssuedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                .sign(Algorithm.HMAC512(secretKey.getBytes()));
    }

    public static Date getTokenExpiration(long expirationTime) {
        Date now = new Date();
        return new Date(now.getTime() + expirationTime);
    }

    public static String getToken(String userId) {
        Date expires = getTokenExpiration(expireTime);
        return JWT.create()
                .withSubject(userId)
                .withExpiresAt(expires)
                .withIssuer(ISSUER)
                .withIssuedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                .sign(Algorithm.HMAC512(secretKey.getBytes()));
    }

    public static JWTVerifier getVerifier() {
        return JWT
                .require(Algorithm.HMAC512(secretKey.getBytes()))
                .withIssuer(ISSUER)
                .build();
    }
    public static void handleError(String token) {
        JWTVerifier verifier = JWT
                .require(Algorithm.HMAC512(secretKey.getBytes()))
                .withIssuer(ISSUER)
                .build();
        System.out.println(ISSUER);
        System.out.println(secretKey.getBytes().toString());
        try {
            verifier.verify(token.replace("Bearer ", ""));
        }         catch (Exception ex) {
            System.out.println(ex);
            throw ex;
        }
//        System.out.println("핸들쪽" + token);
//        JWTVerifier verifier = JWT
//                .require(Algorithm.HMAC256(secretKey.getBytes()))
//                .withIssuer(ISSUER)
//                .build();
//        System.out.println("베리" + verifier);
////        Jws<Claims> jws;
////        jws = Jwts.parserBuilder() // (1)
////                .setSigningKey(secretKey)       // (2)
////                .build()                  // (3)
////                .parseClaimsJws(token);
//
//        try {
//            verifier.verify(token.replace(TOKEN_PREFIX, ""));
//        } catch (Exception ex) {
//            log.error(ex.toString());
//            throw ex;
//        }
    }

    //jwt 인증 정보 조회
    public Authentication getAuthentication(String token){
//        System.out.println("여기 오냐!");
        System.out.println(token);
//        System.out.println("리플레이스 테스트" + token.replace("Bearer ", ""));
        if (token != null) {
            // parse the token and validate it (decode)
//            System.out.println("1");
            JWTVerifier verifier = getVerifier();
//            System.out.println("2");
            handleError(token);
//            System.out.println("3");
            DecodedJWT decodedJWT = verifier.verify(token.replace("Bearer ", ""));
//            System.out.println("4");
            String userId = decodedJWT.getSubject();
//            System.out.println("여기 오냐?");
//            System.out.println("유저 아이디 확인" + userId);
            if (userId != null) {
                // jwt 토큰에 포함된 계정 정보(userId) 통해 실제 디비에 해당 정보의 계정이 있는지 조회.
                System.out.println("들어오니?" + userId);
                User user = userRepository2.findUserByUserId(userId).orElse(null);
                System.out.println("유저확인" + user);
                if (user != null) {
                    // 식별된 정상 유저인 경우, 요청 context 내에서 참조 가능한 인증 정보(jwtAuthentication) 생성.
                    FWUserDetails userDetails = new FWUserDetails(user);
                    UsernamePasswordAuthenticationToken jwtAuthentication = new UsernamePasswordAuthenticationToken(userId,
                            null, userDetails.getAuthorities());
                    jwtAuthentication.setDetails(userDetails);
                    return jwtAuthentication;
                }
            }
        }
        return null;
//        UserDetails userDetails =userDetailsService.loadUserByUsername(this.getUserPk(token));
////
////
//        return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
    }

    public String getUserPk(String token){
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
    }

    // Request의 Header에서 token 값을 가져옴 "X-AUTH-TOKEN" : "TOKEN"
    public String resolveToken(HttpServletRequest request){
        System.out.println(request);
        return request.getHeader("authorization");
    }

    public String getSubject(String tokenBearer){

        String token = tokenBearer.substring("Bearer ".length());
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public Integer getIdAtToken() {
        // header 에서 빼오는 거
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String id = request.getHeader("authorization");
        return Integer.parseInt(getSubject(id));
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
