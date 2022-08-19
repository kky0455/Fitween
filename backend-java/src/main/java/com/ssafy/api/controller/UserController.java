package com.ssafy.api.controller;


import com.ssafy.api.request.UserInfoDto;
import com.ssafy.api.request.ArticleImgDto;
import com.ssafy.api.request.ArticleListDto;
import com.ssafy.api.request.UserProfileDto;
import com.ssafy.api.request.UserUpdateDto;
import com.ssafy.api.service.FollowService;
import com.ssafy.api.service.UserService;
import com.ssafy.common.auth.FWUserDetails;
import com.ssafy.db.dto.Message;
import com.ssafy.db.entity.Article;
import com.ssafy.db.entity.ArticleImg;
import com.ssafy.db.entity.Follow;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.ArticleRepository;
import com.ssafy.db.repository.FollowRepository;
import com.ssafy.db.repository.UserRepository;
import com.ssafy.db.repository.UserRepository2;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 유저 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "유저 API", tags = {"User"})
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    FollowService followService;
    @Autowired
    UserService userService;
    @Autowired
    FollowRepository followRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserRepository2 userRepository2;
    @Autowired
    ArticleRepository articleRepository;
    @ApiOperation(value = "사용자의 상세 정보를 반환한다.", response = User.class)
    @GetMapping("/{userId}")
    public ResponseEntity<?> findUser(@PathVariable String userId, @ApiIgnore Authentication authentication){

        FWUserDetails userDetails = (FWUserDetails) authentication.getDetails();

        User user = userService.getUserByUserId(userId);
        int articleCount = user.getArticles().size();
        int followingCount = followRepository.countByFrom(user);
        int followerCount = followRepository.countByTo(user);
        boolean isFollowed = followService.isFollow(userDetails.getUser(), user);
        UserProfileDto userProfileDto = new UserProfileDto(user.getUserId(), user.getNickname(), articleCount, followerCount, followingCount, isFollowed);
        return ResponseEntity.status(200).body(userProfileDto);
    }

    @ApiOperation(value = "사용자의 상세 정보를 반환한다.", response = User.class)
    @GetMapping("/articlelist")
    public ResponseEntity<?> findArticle(@RequestParam String userId, @ApiIgnore Authentication authentication){

        List<Object> articleList = new ArrayList<>();
        User user = userRepository2.findUserByUserId(userId).orElse(null);
        if (user != null) {
            List<Article> articles = articleRepository.findAllByUser(user).orElse(null);
            if (articles != null) {
                articles.forEach(article -> {
                    Map<String, Object> result = new HashMap<>();
                    result.put("baseUrl" ,article.getArticleImgs().get(0).getBaseUrl());
                    result.put("img", article.getArticleImgs().get(0).getImg());
                    ArticleListDto articleListDto = new ArticleListDto(article.getArticleIdx(), result);
                    articleList.add(articleListDto);
                });
                return ResponseEntity.status(200).body(articleList);
            }
        }
        return ResponseEntity.status(200).body(articleList);

    }
    @ApiOperation(value = "회원 정보 수정")
    @PutMapping()
    @ApiResponses({ @ApiResponse(code = 200, message = "회원정보 수정 완료"),
//            @ApiResponse(code = 401, message = "인증 실패"),
//            @ApiResponse(code = 404, message = "사용자 없음"),
//            @ApiResponse(code = 500, message = "해당 게시글이 없습니다.")
    })
    public ResponseEntity<?> updateInfo(@RequestBody UserUpdateDto updateUserDto, @ApiIgnore Authentication authentication) throws Exception {
        FWUserDetails userDetails = (FWUserDetails) authentication.getDetails();
        userService.updateUser(userDetails.getUserId(), updateUserDto);
        return ResponseEntity.status(200).body("회원정보 수정 완료");
    }
    @ApiOperation(value = "회원 탈퇴")
    @DeleteMapping()
    @ApiResponses({ @ApiResponse(code = 200, message = "회원 탈퇴 성공"),
//            @ApiResponse(code = 401, message = "인증 실패"),
//            @ApiResponse(code = 404, message = "사용자 없음"),
//            @ApiResponse(code = 500, message = "해당 게시글이 없습니다.")
    })
    public ResponseEntity<String> userdelete( @ApiIgnore Authentication authentication) throws Exception {
        FWUserDetails userDetails = (FWUserDetails) authentication.getDetails();
        User user = userDetails.getUser();
        List<Follow> followList = followRepository.findAllByFrom(user);
        followList.forEach(follow -> {
            followRepository.delete(follow);
        });
        userRepository2.delete(user);
        return ResponseEntity.status(200).body("회원 탈퇴 성공");
    }

    @ApiOperation(value = "사용자의 상세 정보를 반환한다.", response = User.class)
    @GetMapping("/info")
    public ResponseEntity<?> findUser(@ApiIgnore Authentication authentication){

        FWUserDetails userDetails = (FWUserDetails) authentication.getDetails();
        String userId = userDetails.getUser().getUserId();
        User user = userRepository2.findUserByUserId(userId).orElse(null);
        UserInfoDto userInfoDto = UserInfoDto.builder()
                .nickname(user.getNickname())
                .height(user.getHeight())
                .weight(user.getWeight())
                .footSize(user.getFootSize())
                .gender(user.getGender())
                .region(user.getRegion())
                .dateOfBirth(user.getDateOfBirth())
                .build();
        return ResponseEntity.status(200).body(userInfoDto);

    }
}