package com.ssafy.api.controller;

import com.ssafy.api.service.FollowService;
import com.ssafy.api.service.UserService;
import com.ssafy.common.auth.FWUserDetails;
import com.ssafy.db.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RequiredArgsConstructor
@RestController
@RequestMapping("/follow")
public class FollowController {
    private final UserService userService;
    private final FollowService followService;

    @PostMapping("/{toUserId}")
    public ResponseEntity<?> followUser(@PathVariable String toUserId, @ApiIgnore Authentication authentication) {
        FWUserDetails userDetails = (FWUserDetails) authentication.getDetails();
        User user = userService.getUserByUserId(toUserId);
        followService.follow(userDetails.getUser(), user);
        return ResponseEntity.status(200).body("팔로우 성공");
    }

    @DeleteMapping("/{toUserId}")
    public ResponseEntity<?> unFollowUser(@PathVariable String toUserId, @ApiIgnore Authentication authentication) {
        FWUserDetails userDetails = (FWUserDetails) authentication.getDetails();
        User user = userService.getUserByUserId(toUserId);
        followService.unFollow(userDetails.getUser(), user);
        return ResponseEntity.status(200).body("팔로우 취소 성공");
    }
}
