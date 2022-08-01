package com.ssafy.api.controller;

import com.ssafy.api.service.ArticleService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Article;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Api(value = "게시물 API", tags = { "Article" })
@RestController
@RequestMapping("/api/v1/articles")
public class ArticleController {
//    public static final Logger logger = LoggerFactory.getLogger(ArticleController.class);
    private static final String SUCCESS = "success";
//    private static final String FAIL = "fail";

    @Autowired
    ArticleService articleService;

    @PostMapping("/register")
    @ApiOperation(value="게시글 등록 (token)", notes="<strong>게시글을 등록</strong>시켜줍니다. user_id는 빈 괄호(\"\")를 입력하여 주세요.")
    @ApiResponses({ @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")})
    public ResponseEntity createArticle(@RequestBody SaveArticleDto saveArticleDto, @ApiIgnore Authentication authentication)
    {
        SsafyUserDetails userDetails = (SsafyUserDetails) authentication.getDetails();
        Long user_idx = userDetails.getUserIdx();

        Article article;
        try {
            saveArticleDto.setUser_idx(user_idx);
            article = articleService.createArticle(saveArticleDto);
        }catch(Exception E) {
            E.printStackTrace();
            System.out.println("게시글 생성 실패");
            return  ResponseEntity.status(500).body("디비 트랜잭션 오류로 인한 생성 실패");
        }
        System.out.println("잘 됨?"+ article.toString());
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

}
