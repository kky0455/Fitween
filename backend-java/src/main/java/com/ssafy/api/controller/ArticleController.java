package com.ssafy.api.controller;

import com.ssafy.api.request.ArticleImgDto;
import com.ssafy.api.request.ArticleInfoDto;
import com.ssafy.api.request.SaveArticleDto;
import com.ssafy.api.request.UpdateArticleDto;
import com.ssafy.api.service.ArticleService;
import com.ssafy.api.service.LikeService;
import com.ssafy.api.service.UserService;
import com.ssafy.api.service.StorageService;
import com.ssafy.common.auth.FWUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.dto.Response;
import com.ssafy.db.entity.Article;
import com.ssafy.db.entity.ArticleImg;
import com.ssafy.db.entity.User;
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
import java.util.*;

@Api(value = "게시물 API 정보를 제공하는 Controller")
@RestController
@RequestMapping("/article")
public class ArticleController {
    public static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    @Autowired
    ArticleService articleService;
    @Autowired
    UserService userService;
    @Autowired
    LikeService likeService;

    ArticleRepository articleRepository;

    //image upload
    private final StorageService storageService;

    @Autowired
    public ArticleController(StorageService storageService){
        this.storageService = storageService;
    }
    //==========


    @PostMapping("/regist")
    @ApiResponses({ @ApiResponse(code = 200, message = "게시물 등록 성공"),
//            @ApiResponse(code = 401, message = "인증 실패"),
//            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "게시물 등록 실패")
    })
    public ResponseEntity createArticle(@RequestBody SaveArticleDto saveArticleDto, @ApiIgnore Authentication authentication)
    {
        FWUserDetails userDetails = (FWUserDetails) authentication.getDetails();
        Map<String, Object> result = new HashMap<>();
        try {
//            articleService.createArticle(saveArticleDto, userDetails.getUser());
            result.put("article_idx", articleService.createArticle(saveArticleDto, userDetails.getUser()));
            result.put("result", "게시물 등록 성공");
            return ResponseEntity.ok().body(result);
        } catch (Exception E) {
            return ResponseEntity.status(500).body("게시물 등록 실패");
        }

//        return ResponseEntity.status(200).body("게시물 등록 성공");
    }
    @ApiOperation(value = "게시글 정보 수정(token)", notes = "게시글 정보 수정")
    @PutMapping("/{article_idx}")
    @ApiResponses({ @ApiResponse(code = 200, message = "게시물 업데이트 성공"),
//            @ApiResponse(code = 401, message = "인증 실패"),
//            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "해당 게시글이 없습니다.")
    })
    public ResponseEntity<?> update(@PathVariable Long article_idx, @RequestBody UpdateArticleDto updateArticleDto, @ApiIgnore Authentication authentication) throws Exception {
        Article article = articleService.findArticle(article_idx);
        try {
            articleService.updateArticle(article, updateArticleDto);
        }catch(NoSuchElementException E) {
            return  ResponseEntity.status(500).body("해당 게시글이 없습니다.");
        }
        return ResponseEntity.status(200).body("게시물 업데이트 성공");
    }
    @ApiOperation(value = "해당 게시글 삭제", notes = "해당 게시글 삭제")
    @DeleteMapping("/{article_idx}")
    @ApiResponses({ @ApiResponse(code = 200, message = "{article_idx}번 해당 게시글 삭제"),
//            @ApiResponse(code = 401, message = "인증 실패"),
//            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "해당 게시글이 없습니다.")
    })
    public ResponseEntity<String> articledelete(@PathVariable("article_idx") Long article_idx) throws Exception {
        Article article;
        try {
            article = articleService.findArticle(article_idx);
            articleService.deleteArticle(article);
        }catch(Exception e ) {
            e.printStackTrace();
            System.out.println("게시글 삭제 실패");
            return  ResponseEntity.status(500).body("해당 게시글 없습니다.");
        }
        return ResponseEntity.status(200).body(article.getArticleIdx()+"번 해당 게시글 삭제");
    }
    @ApiOperation(value="게시글 전체 조회", notes="<strong>게시글을 전체 조회를</strong>시켜줍니다.")
    @GetMapping("/list")
    public ResponseEntity<?> findAllArticle(@ApiIgnore Authentication authentication){

        FWUserDetails userDetails = (FWUserDetails) authentication.getDetails();
        List<Article> articles = articleService.findAllArticle();
        articles.forEach(article -> {
            article.setLikesCount(article.getLikes().size());
            article.updateLikeStatus(likeService.isLike(userDetails.getUser(), article));
        });
        return ResponseEntity.status(200).body(articles);
    }
    @GetMapping("/detail/{article_idx}")
    @ApiOperation(value ="게시글 상세  조회", notes ="해당 article_idx 게시판 정보 출력")
    public ResponseEntity<?> findOneArticle(@PathVariable Long article_idx, @ApiIgnore Authentication authentication) {
        Article article = articleService.findArticle(article_idx);
        FWUserDetails userDetails = (FWUserDetails) authentication.getDetails();
        User user = userDetails.getUser();
        boolean likeStatus = likeService.isLike(user, article);
        List<Object> imgUrl = new ArrayList<>();
        article.getArticleImgs().forEach(articleImg -> {
            ArticleImgDto articleImgDto = new ArticleImgDto(articleImg.getBaseUrl(), articleImg.getImg());
            imgUrl.add(articleImgDto);
        });
        ArticleInfoDto articleInfoDto = new ArticleInfoDto(article, likeStatus, imgUrl);
        return ResponseEntity.status(200).body(articleInfoDto);
    }


    @PostMapping("/like/{article_idx}")

    @ApiOperation(value ="게시글 좋아요", notes ="해당 article_idx에 좋아요")
    @ApiResponses({ @ApiResponse(code = 200, message = "좋아요 성공"),
//            @ApiResponse(code = 401, message = "인증 실패"),
//            @ApiResponse(code = 404, message = "사용자 없음"),
//            @ApiResponse(code = 500, message = "해당 게시글이 없습니다.")
    })
    public ResponseEntity<?> articlelike(@PathVariable Long article_idx, @ApiIgnore Authentication authentication) {
        FWUserDetails userDetails = (FWUserDetails) authentication.getDetails();
        User user = userDetails.getUser();
        Article article = articleService.findArticle(article_idx);
        likeService.likes(user, article);
        return ResponseEntity.status(200).body("좋아요 성공");
    }

    @DeleteMapping("/like/{article_idx}")

    @ApiOperation(value ="게시글 좋아요 취소", notes ="해당 article_idx에 좋아요 취소")
    @ApiResponses({ @ApiResponse(code = 200, message = "좋아요 취소"),
//            @ApiResponse(code = 401, message = "인증 실패"),
//            @ApiResponse(code = 404, message = "사용자 없음"),
//            @ApiResponse(code = 500, message = "해당 게시글이 없습니다.")
    })
    public ResponseEntity<?> articleunlike(@PathVariable Long article_idx, @ApiIgnore Authentication authentication) {
        FWUserDetails userDetails = (FWUserDetails) authentication.getDetails();
        User user = userDetails.getUser();
        Article article = articleService.findArticle(article_idx);
        likeService.unLikes(user, article);
        return ResponseEntity.status(200).body("좋아요 취소");
    }

    @PostMapping("/registtest")
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
