package com.ssafy.api.controller;

import com.ssafy.api.request.SaveArticleDto;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.NoSuchElementException;

@Api(value = "게시물 API", tags = { "Article" })
@RestController
@RequestMapping("/api/v1/articles")
public class ArticleController {
    public static final Logger logger = LoggerFactory.getLogger(ArticleController.class);
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

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

    @ApiOperation(value = "게시글 정보 수정(token)", notes = "게시글 정보 수정")
    @ApiResponses({ @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류") })
    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody SaveArticleDto saveArticleDto, @ApiIgnore Authentication authentication) throws Exception {
        Article article;
        SsafyUserDetails userDetails = (SsafyUserDetails) authentication.getDetails();
        Long user_idx = userDetails.getUserIdx();
        try {
            System.out.println(saveArticleDto.toString());
            saveArticleDto.setUser_idx(user_idx);
            article = articleService.findByArticleId(saveArticleDto.getArticle_idx());
        }catch(NoSuchElementException E) {
            System.out.println("게시글 수정 실패");
            return  ResponseEntity.status(500).body("해당 게시글이 없어서 게시글 수정 실패");
        }
        Article updateArticle = articleService.updateArticle(article, saveArticleDto);
        System.out.println("업데이트 됨");
        return new ResponseEntity<String>(SUCCESS+"\n"+updateArticle.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "해당 게시글 삭제", notes = "해당 게시글 삭제")
    @ApiResponses({ @ApiResponse(code = 200, message = "게시글 삭제 성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "해당 회원 없음")})
    @DeleteMapping("/delete/{article_idx}")
    public ResponseEntity<String> articledelete(@PathVariable("article_idx") Long article_idx) throws Exception {
        Article article;
        try {
            article = articleService.findByArticleId(article_idx);
            articleService.deleteArticle(article);
        }catch(Exception e ) {
            e.printStackTrace();
            System.out.println("게시글 삭제 실패");
            return  ResponseEntity.status(500).body("해당 게시글 없어서 삭제 "+FAIL);
        }
        logger.debug("해당 게시글 삭제 성공");
        return ResponseEntity.status(200).body(article.getArticleIdx()+"번 해당 게시글 삭제"+SUCCESS);
    }

    @ApiOperation(value="게시글 전체 조회", notes="<strong>게시글을 전체 조회를</strong>시켜줍니다.")
    @ApiResponses({ @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")})
    @GetMapping("/list")
    public ResponseEntity<List<Article>> findAllArticle(){
        List<Article> articles = articleService.findAllArticle();

        return new ResponseEntity<List<Article>>(articles,HttpStatus.OK);
    }

    @GetMapping("/detail/{article_idx}")
    @ApiOperation(value ="게시글 상세  조회", notes ="해당 article_idx 게시판 정보 출력")
    @ApiResponses({ @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류") })
    public ResponseEntity<Article> findOneArticle(@PathVariable Long article_idx) {
        Article article = articleService.findByArticleId(article_idx);
        return new ResponseEntity<Article>(article, HttpStatus.OK);
    }

}
