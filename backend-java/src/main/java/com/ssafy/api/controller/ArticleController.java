package com.ssafy.api.controller;

import com.ssafy.api.request.ArticleInfoDto;
import com.ssafy.api.request.SaveArticleDto;
import com.ssafy.api.request.UpdateArticleDto;
import com.ssafy.api.service.ArticleService;
import com.ssafy.api.service.StorageService;
import com.ssafy.common.auth.FWUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.dto.Response;
import com.ssafy.db.entity.Article;
import com.ssafy.db.repository.ArticleRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Api(value = "게시물 API", tags = { "Article" })
@RestController
@RequestMapping("/article")
public class ArticleController {
    public static final Logger logger = LoggerFactory.getLogger(ArticleController.class);
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @Autowired
    ArticleService articleService;

    ArticleRepository articleRepository;

    //image upload
    private final StorageService storageService;

    @Autowired
    public ArticleController(StorageService storageService){
        this.storageService = storageService;
    }
    //==========


    @PostMapping("/register")
    @ApiOperation(value="게시글 등록 (token)", notes="<strong>게시글을 등록</strong>시켜줍니다. user_id는 빈 괄호(\"\")를 입력하여 주세요.")
    @ApiResponses({ @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")})
    public ResponseEntity createArticle(@RequestBody SaveArticleDto saveArticleDto, @ApiIgnore Authentication authentication)
    {
//        FWUserDetails userDetails = (FWUserDetails) authentication.getDetails();
//        Long user_idx = userDetails.getUserIdx();

        try {
            articleService.createArticle(saveArticleDto, authentication);
        }catch(Exception E) {
            E.printStackTrace();
            System.out.println("게시글 생성 실패");
            return  ResponseEntity.status(500).body("디비 트랜잭션 오류로 인한 생성 실패");
        }
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @ApiOperation(value = "게시글 정보 수정(token)", notes = "게시글 정보 수정")
    @ApiResponses({ @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류") })
    @PutMapping("/{article_idx}")
    public ResponseEntity<?> update(@PathVariable Long article_idx, @RequestBody UpdateArticleDto updateArticleDto, @ApiIgnore Authentication authentication) throws Exception {
//        FWUserDetails userDetails = (FWUserDetails) authentication.getDetails();
//        Long user_idx = userDetails.getUserIdx();
        Article article = articleService.findByArticleId(article_idx);
        try {
//            article = articleService.findByArticleId(article_idx);
            articleService.updateArticle(article, updateArticleDto);
        }catch(NoSuchElementException E) {
            System.out.println("게시글 수정 실패");
            return  ResponseEntity.status(500).body("해당 게시글이 없어서 게시글 수정 실패");
        }
//        article.updateArticle(updateArticleDto);
        System.out.println("업데이트 됨");
        return new ResponseEntity<>(SUCCESS+"\n"+article, HttpStatus.OK);
    }

    @ApiOperation(value = "해당 게시글 삭제", notes = "해당 게시글 삭제")
    @ApiResponses({ @ApiResponse(code = 200, message = "게시글 삭제 성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "해당 회원 없음")})
    @DeleteMapping("/{article_idx}")
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

//    @GetMapping("/detail/{article_idx}")
//    @ApiOperation(value ="게시글 상세  조회", notes ="해당 article_idx 게시판 정보 출력")
//    @ApiResponses({ @ApiResponse(code = 200, message = "성공"),
//            @ApiResponse(code = 401, message = "인증 실패"),
//            @ApiResponse(code = 404, message = "사용자 없음"),
//            @ApiResponse(code = 500, message = "서버 오류") })
//    public ResponseEntity<Article> findOneArticle(@PathVariable Long article_idx) {
//        Article article = articleService.findByArticleId(article_idx);
//        return new ResponseEntity<Article>(article, HttpStatus.OK);
//    }

    @GetMapping("/detail/{article_idx}")
    @ApiOperation(value ="게시글 상세  조회", notes ="해당 article_idx 게시판 정보 출력")
    @ApiResponses({ @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류") })
    public ResponseEntity<?> findOneArticle(@PathVariable Long article_idx, @ApiIgnore Authentication authentication) {
        FWUserDetails userDetails = (FWUserDetails) authentication.getDetails();
        Long user_idx = userDetails.getUser().getUserIdx();
        ArticleInfoDto articleInfoDto = new ArticleInfoDto();
        articleInfoDto.setId(article_idx);
        Article article = articleService.findByArticleId(article_idx);
        articleInfoDto.setTitle(article.getTitle());
        articleInfoDto.setContent(article.getContent());
        articleInfoDto.setPrice(article.getPrice());
        articleInfoDto.setLikesCount(article.getLikes().size());
        article.getLikes().forEach(likes -> {
            if(likes.getUser().getUserIdx() == user_idx)articleInfoDto.setLikesState(true);
        });
        return new ResponseEntity<>(articleInfoDto, HttpStatus.OK);
    }

    @PostMapping("/regist")
    public ResponseEntity<?> upload(@RequestParam (value="photo",required = false) MultipartFile[] photo, @RequestParam String title ) throws Exception {
        Response res = new Response();
        List<String> results = new ArrayList<>();
        List<String> imageLocations = new ArrayList<>();
        System.out.println(photo);
        System.out.println(title);

        try{
            results = storageService.saveFiles(photo, title);
            for(String result : results){
                imageLocations.add("/"+title+"/"+result);
            }
            res.setImageLocations(imageLocations);
            res.setMessage("done");
            res.setSuccess(true);
            return new ResponseEntity<Response>(res, HttpStatus.OK);
        }catch (Exception e){
            res.setMessage("failed");
            res.setSuccess(false);
            return new ResponseEntity<Response>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/display/{userId}/{articleTitle:.+}")
    public ResponseEntity<Resource> displayImage(@PathVariable String articleTitle,
                                                 @PathVariable String userId,
                                                 HttpServletRequest request) {
        // Load file as Resource
        Resource resource = storageService.loadFileAsResource(userId, articleTitle);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
