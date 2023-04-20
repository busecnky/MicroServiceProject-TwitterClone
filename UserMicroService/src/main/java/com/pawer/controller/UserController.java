package com.pawer.controller;


import com.pawer.dto.request.*;
import com.pawer.dto.response.FindAllRequestsResponse;
import com.pawer.dto.response.FindByIdResponseDto;
import com.pawer.dto.response.ProfileCartResponse;
import com.pawer.repository.entity.User;
import com.pawer.service.FollowService;
import com.pawer.service.FollowerService;
import com.pawer.service.UserService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final FollowerService followerService;
    private final FollowService followService;

    @PostMapping("/createpost")
    @CrossOrigin("*")
    public ResponseEntity<Boolean> createPost(@RequestBody CreatePostDto dto) {
        System.out.println("bahça duvarından aştım");
        return ResponseEntity.ok(userService.createPost(dto));
    }
    @PostMapping("/update")
    @CrossOrigin("*")
    public ResponseEntity<Boolean> userUpdate(@RequestBody UpdateUserProfileRequestDto updateUserProfileRequestDto){
        return ResponseEntity.ok(userService.updateUserProfile(updateUserProfileRequestDto));
    }
    @PostMapping("/findbyid")
    @CrossOrigin("*")
    public ResponseEntity<FindByIdResponseDto> findById(FindByIdRequestDto dto){
        return ResponseEntity.ok(userService.findByIdFromToken(dto));
    }

    @PostMapping("/findme")
    @CrossOrigin("*")
    public ResponseEntity<FindByIdResponseDto> findMe(@RequestBody FindByIdRequestDto dto){
        return ResponseEntity.ok(userService.findMe(dto));
    }
    @PostMapping("/createcommenttopost")
    @CrossOrigin("*")
    public ResponseEntity<Boolean> createCommentToPost(@RequestBody CommentToPostDto dto){

        return ResponseEntity.ok(userService.createCommentToPost(dto));
    }

    @PostMapping("/createlikepost")
    @CrossOrigin("*")
    public ResponseEntity<Boolean> createLikePost(@RequestBody LikePostRequestDto dto){
        return ResponseEntity.ok(userService.createLikePost(dto));
    }
    @PostMapping("/findlikepost")
    @CrossOrigin("*")
    public ResponseEntity<Void> findLikePost(@RequestBody  FindPostRequestDto dto){
       // userService.findLikePost(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/follow")
    @CrossOrigin("*")
    public  ResponseEntity<Integer> followUser(@RequestBody FollowingUserRequestDto dto){
        return ResponseEntity.ok(followService.followUser(dto));
    }

    @PostMapping("/acceptfollower")
    @CrossOrigin("*")
    public  ResponseEntity<Integer> acceptFollower(@RequestBody AcceptFollowerRequestDto dto){
        return  ResponseEntity.ok(followerService.acceptFollower(dto));
    }
    @PostMapping("/rejectfollower")
    @CrossOrigin("*")
    public  ResponseEntity<Integer> rejectFollower(@RequestBody AcceptFollowerRequestDto dto){
        return  ResponseEntity.ok(followerService.rejectFollower(dto));
    }
    @GetMapping("/findalluser")
    @ResponseBody
    @CrossOrigin("*")
    public ResponseEntity<List<User>> findAllUser(){
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping("/findallcarts")
    @CrossOrigin("*")
    public ResponseEntity<List<ProfileCartResponse>> findAllForUserCart(@RequestBody ProfileCartRequestDto dto){
        System.out.println(dto.getToken());
        return ResponseEntity.ok(userService.isFollow(dto));
    }

    @PostMapping("/findallrequests")
    @ResponseBody
    @CrossOrigin("*")
    public ResponseEntity<List<FindAllRequestsResponse>> findAllRequests(@RequestBody FindAllRequestsRequestDto dto){
        return ResponseEntity.ok(followerService.findAllRequests(dto));
    }

    @PostMapping("/findallrequestscount")
    @ResponseBody
    @CrossOrigin("*")
    public ResponseEntity<Integer> findAllRequestsCount(@RequestBody FindAllRequestsRequestDto dto){
        return ResponseEntity.ok(followerService.findAllRequestsCount(dto));
    }

//    @PostMapping("/removefollower")
//    @CrossOrigin("*")
//    public  ResponseEntity<Integer> removeFollower(@RequestBody AcceptFollowerRequestDto dto){
//        return  ResponseEntity.ok(userService.removeFollower(dto));
//    }

}
