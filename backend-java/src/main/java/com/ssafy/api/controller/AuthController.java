package com.ssafy.api.controller;

import com.ssafy.api.service.UserService;
import com.ssafy.db.dto.Message;
import com.ssafy.db.dto.StatusEnum;
import com.ssafy.db.dto.UserSaveRequestDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;

import java.nio.charset.Charset;


/**
 * 인증 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "인증 API", tags = {"Auth."})
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    @ApiOperation(value = "회원가입",notes = "email과 password를 받아서 회원가입을 한다.")
    @PostMapping("/login")
    public ResponseEntity<?> signUp(@RequestBody UserSaveRequestDto requestDto){
        Message message = new Message();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        try {
            String userid = userService.join(requestDto.toEntity());

            message.setStatus(StatusEnum.OK);
            message.setMessage("회원가입 성공");
            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } catch (IllegalStateException e){
            e.printStackTrace();
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage("가입 이메일이 중복됩니다.");
            return new ResponseEntity<String>("OVERLAP", HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            e.printStackTrace();
            message.setStatus(StatusEnum.INTERNAL_SERVER_ERROR);
            message.setMessage("서버 에러 발생");
            return new ResponseEntity<>(message, headers,  HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }




}
