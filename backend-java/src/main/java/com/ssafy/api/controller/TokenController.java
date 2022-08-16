package com.ssafy.api.controller;

import com.nimbusds.oauth2.sdk.token.RefreshToken;
import com.ssafy.api.request.UserLoginPostReq;
import com.ssafy.api.service.UserService;
import com.ssafy.common.auth.JwtTokenProvider;
import com.ssafy.db.dto.Message;
import com.ssafy.db.dto.StatusEnum;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;
import java.nio.file.AccessDeniedException;

@RequiredArgsConstructor
@RestController
public class TokenController {

    private final UserService userService;
    private UserLoginPostReq loginPostReq;

    @GetMapping("/token/expired")
    public String auth() {
        throw new RuntimeException();
    }
    @ApiOperation(value = "access token 재발급 요청", notes = "refreshtoken으로 accesstoken 재발급")
    @PostMapping("/token/refresh")
    public ResponseEntity<?> tokenRefresh(@RequestBody String refreshToken) throws Exception {
    Message message = new Message();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        System.out.println(refreshToken+"  : refresh");

        try {
            loginPostReq = userService.refreshToken(refreshToken);
            System.out.println("check");
            String userid = loginPostReq.getUserId();
            String accesstoken = loginPostReq.getAccessToken();
            System.out.println(accesstoken+"    : access");
            System.out.println(userid+"    :   userid ");

            message.setStatus(StatusEnum.OK);
            message.setUserId(userid);
            message.setAccessToken(accesstoken);
            message.setRefreshToken(refreshToken);
            return new ResponseEntity<>(message, headers, HttpStatus.OK);
    } catch (AccessDeniedException e) {
        e.printStackTrace();
        message.setStatus(StatusEnum.UNAUTHORIZED);
        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    } catch (IllegalStateException e) {
        e.printStackTrace();
        return new ResponseEntity<String>("RE LOGIN", HttpStatus.PAYMENT_REQUIRED);
    } catch (Exception e) {
        e.printStackTrace();
        message.setStatus(StatusEnum.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(message, headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
}
