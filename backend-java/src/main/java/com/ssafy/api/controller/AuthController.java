package com.ssafy.api.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.ssafy.api.request.UserLoginPostReq;
import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.api.response.UserLoginPostRes;
import com.ssafy.api.service.UserService;
import com.ssafy.db.dto.Message;
import com.ssafy.db.dto.StatusEnum;
import com.ssafy.db.entity.User;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames.CLIENT_ID;


/**
 * 인증 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "인증 API", tags = {"Auth."})
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@PropertySource("classpath:/application-oauth.properties")
public class AuthController {

    @Autowired
    ApplicationContext context;

    private final UserService userService;

    @ApiOperation(value = "로그인", notes = "서비스에서 보내준 idToken을 활용하여 로그인 요청")
    @GetMapping("/duplicationcheck")
    public ResponseEntity<?> duplicationcheck(@RequestParam String nickname) {
        User checkUser = userService.checkNicknameDuplicate(nickname);
        Map<String, Object> result = new HashMap<>();
        if (checkUser == null) {
            result.put("isSuccess", false);
            return ResponseEntity.status(200).body(result);
        } else {
            result.put("isSuccess", true);
            return ResponseEntity.status(200).body(result);
        }
    }

    @ApiOperation(value = "로그인", notes = "서비스에서 보내준 idToken을 활용하여 로그인 요청")
    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestParam String authCode) throws GeneralSecurityException, IOException {

        Message message = new Message();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        // oauth properties client id 받아오기
        Environment env = context.getEnvironment();
        String clientId = env.getProperty("spring.security.oauth2.client.registration.google.client-id");


        // from google

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())

                // Specify the CLIENT_ID of the app that accesses the backend:
                .setAudience(Collections.singletonList(clientId))    //oauth 클라이언트 id
                // Or, if multiple clients access the backend:
                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build();

        // (Receive idTokenString by HTTPS POST)

        GoogleIdToken idToken = verifier.verify(authCode);

        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();

            // Print user identifier
            String userId = payload.getSubject();
            System.out.println("User ID: " + userId);

            // Get profile information from payload
            String email = payload.getEmail();
            boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");
            String locale = (String) payload.get("locale");
            String familyName = (String) payload.get("family_name");
            String givenName = (String) payload.get("given_name");


            try{
                //User user = userService.getUserByUserId(userId);
                UserLoginPostReq userLogin =userService.userLogin(userId);
                userLogin.setEmail(email);
                userLogin.setProfileImg(pictureUrl);
                message.setStatus(StatusEnum.OK);
                message.setResponseType("signIn");
                message.setUserId(userId);
                message.setAccessToken(userLogin.getAccessToken());
                message.setRefreshToken(userLogin.getRefreshToken());
                headers.add("accessToken",userLogin.getAccessToken());
                headers.add("refreshToken",userLogin.getRefreshToken());
                return new ResponseEntity<>(message, headers, HttpStatus.OK);
            }catch (Exception e){
                //e.printStackTrace();
                message.setStatus(StatusEnum.UNAUTHORIZED);
                message.setResponseType("signUp");
                message.setUserId(userId);
                return new ResponseEntity<>(message, HttpStatus.OK);
            }

        }
        else {
            System.out.println("Invalid ID token.");
        }

        return ResponseEntity.status(401).body(UserLoginPostRes.of(401, "Invalid ID token", null));

    }

    @ApiOperation(value = "회원가입",notes = "email과 password를 받아서 회원가입을 한다.")
    @PostMapping("/signup")
    public  ResponseEntity<?> signUp(@RequestBody UserRegisterPostReq requestDto){

        Message message = new Message();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        Calendar now = Calendar.getInstance();
        Integer currentYear = now.get(Calendar.YEAR);
        System.out.println(requestDto.getDateOfBirth());

        String str_birthYear[] = (requestDto.getDateOfBirth()).split("-");
        int year = Integer.parseInt(str_birthYear[0]);
        int age = (currentYear-year)+1;

        try {
            String id = userService.join(requestDto.toEntity());
            requestDto.setAge(age);
            UserLoginPostReq userLogin =userService.userLogin(requestDto.getUserId());

            headers.add("accessToken",userLogin.getAccessToken());
            headers.add("refreshToken",userLogin.getRefreshToken());
            message.setAccessToken(userLogin.getAccessToken());
            message.setRefreshToken(userLogin.getRefreshToken());
            message.setUserId(id);
            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } catch (IllegalStateException e){
            e.printStackTrace();
            message.setStatus(StatusEnum.BAD_REQUEST);

            return new ResponseEntity<String>("OVERLAP", HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            e.printStackTrace();
            message.setStatus(StatusEnum.INTERNAL_SERVER_ERROR);

            return new ResponseEntity<>(message, headers,  HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @ApiOperation(value = "로그아웃 요청.",notes = "refresh 토큰으로 로그아웃을 요청한다.")
    @GetMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader(value="refreshToken") String refreshToken) {
        Message message = new Message();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        try {
            userService.logoutMember(refreshToken);
            message.setStatus(StatusEnum.OK);
            message.setResponseType("로그아웃 성공");
            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } catch (Exception e){
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setResponseType("ACCESS TOKEN이 일치하지 않습니다.");
            return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
        }
    }


}



