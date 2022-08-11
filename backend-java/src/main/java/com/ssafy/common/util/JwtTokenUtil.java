package com.ssafy.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.*;

import com.auth0.jwt.interfaces.Claim;
import com.ssafy.common.auth.FWUserDetailService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static org.kurento.jsonrpc.client.JsonRpcClient.log;

/**
 * jwt 토큰 유틸 정의.
 */
@Component
public class JwtTokenUtil {
    private static String secretKey = "dyAeHubOOc8KaOfYB6XEQoEj1QzRlVgtjNL8PYs1A1tymZvvqkcEU7L1imkKHeDa";
//    private static String secretKey = "fitweenSecretadiosfhaiodhfaiodhfiadflkadfklnad,mf";
    private final long refreshExpireTime = 1 * 60 * 2000L;  // 2분
    private static long expirationTime =  1 * 60 * 1000L;   // 1분

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "authorization";
    public static final String ISSUER = "fitween.com";


//    @Autowired
//	public JwtTokenUtil(@Value("${jwt.secret}") String secretKey, @Value("${jwt.expiration}") long expirationTime, FWUserDetailService fwUserDetailService) {
//		this.secretKey = secretKey;
//		this.expirationTime = expirationTime;
//    }
    
	public void setExpirationTime() {
    		//JwtTokenUtil.expirationTime = Integer.parseInt(expirationTime);
    		JwtTokenUtil.expirationTime = expirationTime;
	}

	public static JWTVerifier getVerifier() {
        return JWT
                .require(Algorithm.HMAC512(secretKey.getBytes()))
                .withIssuer(ISSUER)
                .build();
    }
    
    public static String getToken(String userId) {
    		Date expires = JwtTokenUtil.getTokenExpiration(expirationTime);
        return JWT.create()
                .withSubject(userId)
                .withExpiresAt(expires)
                .withIssuer(ISSUER)
                .withIssuedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                .sign(Algorithm.HMAC512(secretKey.getBytes()));
    }

    public static String getToken(Instant expires, String userId) {
        return JWT.create()
                .withSubject(userId)
                .withExpiresAt(Date.from(expires))
                .withIssuer(ISSUER)
                .withIssuedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                .sign(Algorithm.HMAC256(secretKey.getBytes()));
    }
    
    public static Date getTokenExpiration(long expirationTime) {
    		Date now = new Date();
    		return new Date(now.getTime() + expirationTime);
    }

    public static void handleError(String token) {
        JWTVerifier verifier = JWT
                .require(Algorithm.HMAC512(secretKey.getBytes()))
                .withIssuer(ISSUER)
                .build();
        System.out.println(ISSUER);
        System.out.println(secretKey.getBytes().toString());
        try {
            verifier.verify(token.replace(TOKEN_PREFIX, ""));
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

    public static void handleError(JWTVerifier verifier, String token) {
        try {
            verifier.verify(token.replace(TOKEN_PREFIX, ""));
        } catch (AlgorithmMismatchException ex) {
            throw ex;
        } catch (InvalidClaimException ex) {
            throw ex;
        } catch (SignatureGenerationException ex) {
            throw ex;
        } catch (SignatureVerificationException ex) {
            throw ex;
        } catch (TokenExpiredException ex) {
            throw ex;
        } catch (JWTCreationException ex) {
            throw ex;
        } catch (JWTDecodeException ex) {
            throw ex;
        } catch (JWTVerificationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
        }
    }
}
