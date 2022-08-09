package com.ssafy.api.controller;

import com.ssafy.api.service.FollowService;
import com.ssafy.common.auth.FWUserDetails;
import com.ssafy.db.repository.UserRepositorySupport;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RequiredArgsConstructor
@RestController
@RequestMapping("/follow")
public class FollowController {

    private final UserRepositorySupport userRepositorySupport;
    private final FollowService followService;

    @PostMapping("/{toUserId}")
    public ResponseEntity<?> followUser(@PathVariable String toUserId, @ApiIgnore Authentication authentication) {
        FWUserDetails userDetails = (FWUserDetails) authentication.getDetails();
        String user_id = userDetails.getUser().getUserId();
        followService.follow(user_id, toUserId);
        return new ResponseEntity<>("팔로우 성공", HttpStatus.OK);
    }

    @DeleteMapping("/{toUserId}")
    public ResponseEntity<?> unFollowUser(@PathVariable String toUserId, @ApiIgnore Authentication authentication) {
        FWUserDetails userDetails = (FWUserDetails) authentication.getDetails();
        Long user_idx = userDetails.getUser().getUserIdx();
        Long to_user_idx = userRepositorySupport.findUserByUserId(toUserId).get().getUserIdx();
        followService.unFollow(user_idx, to_user_idx);
        return new ResponseEntity<>("팔로우 취소 성공", HttpStatus.OK);
    }
}
