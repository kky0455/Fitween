package com.ssafy.api.controller;

import com.ssafy.api.request.ArticleInfoDto;
import com.ssafy.api.request.SaveArticleDto;
import com.ssafy.api.request.UpdateArticleDto;
import com.ssafy.api.service.ArticleService;
import com.ssafy.api.service.LikeService;
import com.ssafy.api.service.UserService;
import com.ssafy.api.service.StorageService;
import com.ssafy.common.auth.FWUserDetails;
import com.ssafy.db.dto.Response;
import com.ssafy.db.entity.Article;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.ArticleRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Api(value = "게시물 API", tags = { "Article" })
@RestController
@RequestMapping("/article")
@ApiResponses({ @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")})
public class ArticleController {
    public static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    @Autowired
    ArticleService articleService;
    @Autowired
    UserService userService;
    @Autowired
    LikeService likeService;

    ArticleRepository articleRepository;

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    //=====image upload
    private final StorageService storageService;

    @Autowired
    public ArticleController(StorageService storageService){
        this.storageService = storageService;
    }
    //==========


    @PostMapping("/regist")
    @ApiOperation(value="게시글 등록 (token)", notes="<strong>게시글을 등록</strong>시켜줍니다. user_id는 빈 괄호(\"\")를 입력하여 주세요.")
    public ResponseEntity createArticle(@RequestBody SaveArticleDto saveArticleDto, @ApiIgnore Authentication authentication)
    {

        System.out.println(authentication);
        FWUserDetails userDetails = (FWUserDetails) authentication.getDetails();
        try {
            articleService.createArticle(saveArticleDto, userDetails.getUser());
        } catch (Exception E) {
            return ResponseEntity.status(500).body("게시물 등록 실패");
        }
        return ResponseEntity.status(200).body("게시물 등록 성공");
    }
    @ApiOperation(value = "게시글 정보 수정(token)", notes = "게시글 정보 수정")
    @PutMapping("/{article_idx}")
    public ResponseEntity<?> update(@PathVariable Long article_idx, @RequestBody UpdateArticleDto updateArticleDto, @ApiIgnore Authentication authentication) throws Exception {
        Article article = articleService.findArticle(article_idx);
        try {
            articleService.updateArticle(article, updateArticleDto);
        }catch(NoSuchElementException E) {
            return  ResponseEntity.status(500).body("해당 게시글이 없어서 게시글 수정 실패");
        }
        return ResponseEntity.status(200).body("게시물 업데이트 성공");
    }
    @ApiOperation(value = "해당 게시글 삭제", notes = "해당 게시글 삭제")
    @DeleteMapping("/{article_idx}")
    public ResponseEntity<String> articledelete(@PathVariable("article_idx") Long article_idx) throws Exception {
        Article article;
        try {
            article = articleService.findArticle(article_idx);
            articleService.deleteArticle(article);
        }catch(Exception e ) {
            e.printStackTrace();
            System.out.println("게시글 삭제 실패");
            return  ResponseEntity.status(500).body("해당 게시글 없어서 삭제 ");
        }
        return ResponseEntity.status(200).body(article.getArticleIdx()+"번 해당 게시글 삭제");
    }
    @ApiOperation(value="게시글 전체 조회", notes="<strong>게시글을 전체 조회를</strong>시켜줍니다.")
    @GetMapping("/list")
    public ResponseEntity<?> findAllArticle(){
        List<Article> articles = articleService.findAllArticle();
        return ResponseEntity.status(200).body(articles);
    }
    @GetMapping("/detail/{article_idx}")
    @ApiOperation(value ="게시글 상세  조회", notes ="해당 article_idx 게시판 정보 출력")
    public ResponseEntity<?> findOneArticle(@PathVariable Long article_idx, @ApiIgnore Authentication authentication) {
        Article article = articleService.findArticle(article_idx);
        FWUserDetails userDetails = (FWUserDetails) authentication.getDetails();
        User user = userService.getUserByUserIdx(article_idx);
        boolean isLiked = likeService.isLike(user, article);
        ArticleInfoDto articleInfoDto = new ArticleInfoDto(article, isLiked);
        return ResponseEntity.status(200).body(articleInfoDto);
    }
    @PostMapping("detail/{article_idx}/like")
    @ApiOperation(value ="게시글 좋아요", notes ="해당 article_idx에 좋아요")
    public ResponseEntity<?> articlelike(@PathVariable Long article_idx, @ApiIgnore Authentication authentication) {
        FWUserDetails userDetails = (FWUserDetails) authentication.getDetails();
        User user = userService.getUserByUserIdx(article_idx);
        Article article = articleService.findArticle(article_idx);
        likeService.likes(user, article);
        return ResponseEntity.status(200).body("좋아요 성공");
    }
    @DeleteMapping("detail/{article_idx}/like")
    @ApiOperation(value ="게시글 좋아요 취소", notes ="해당 article_idx에 좋아요 취소")
    public ResponseEntity<?> articleunlike(@PathVariable Long article_idx, @ApiIgnore Authentication authentication) {
        FWUserDetails userDetails = (FWUserDetails) authentication.getDetails();
        User user = userService.getUserByUserIdx(article_idx);
        Article article = articleService.findArticle(article_idx);
        likeService.unLikes(user, article);
        return ResponseEntity.status(200).body("좋아요 취소");
    }

    @PostMapping("/registtest")
    @ApiOperation(notes = "사진 업로드", value = "해당 uuid에 따른 폴더 생성 ")
    public ResponseEntity<?> upload(@RequestParam (value="photo",required = false) MultipartFile[] photo) throws Exception {
        Response res = new Response();
        List<String> results = new ArrayList<>();
        List<String> imageLocations = new ArrayList<>();

        UUID uid = UUID.randomUUID();

        try{
            results = storageService.saveFiles(photo, uid);
            for(String result : results){
                imageLocations.add("/"+uid+"/"+result);
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

    @GetMapping("/display/{folderName}/{fileName:.+}")  // =========이후 수정 요망=========
    @ApiOperation(value = "업로드 된 사진 불러오기", notes = "저장된 폴더 내에 사진 불러오기")
    public ResponseEntity<?> displayImage(@PathVariable String folderName, HttpServletRequest request) {

        // Load file as Resource
        //Resource resource = storageService.loadFileAsResource(folderName, fileName);

        File dir= new File(uploadPath+"/"+folderName);
        String images[] = dir.list();


        // Try to determine file's content type
        String contentType = null;
//        String headerMsg="";
        List<String> imageList= new ArrayList<>();

        for (int i = 0; i < images.length; i++) {

            //fileName = images[i];
            Resource resource = storageService.loadFileAsResource(folderName, images[i]);
            String img;

            try (Reader reader = new InputStreamReader(resource.getInputStream())) {
               img = FileCopyUtils.copyToString(reader);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }

            imageList.add(img);

            try {
                contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            } catch (IOException ex) {
                logger.info("Could not determine file type.");
            }
//            headerMsg +="attachment; filename=\"" + resource.getFilename() + "\"";

            System.out.println("inside for state");

        }
        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType(contentType))
//                .header(HttpHeaders.CONTENT_DISPOSITION, headerMsg)
                .body(imageList);
    }

    @GetMapping("/displaytest/{userId}/{articleTitle:.+}")
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
