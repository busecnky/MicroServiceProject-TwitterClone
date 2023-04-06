package com.pawer.controller;


import com.pawer.dto.request.*;
import com.pawer.dto.response.FindByIdResponseDto;
import com.pawer.exception.EErrorType;
import com.pawer.exception.UserException;
import com.pawer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


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

    @PostMapping("/createcommenttopost")
    @CrossOrigin("*")
    public ResponseEntity<Boolean> createCommentToPost(@RequestBody CommentToPostDto dto){
        System.out.println("user controller create comment");
        System.out.println("dto nun post id'si"+dto.getPostId());
        return ResponseEntity.ok(userService.createCommentToPost(dto));
    }

    @PostMapping("/follow")
    @CrossOrigin("*")
    public  ResponseEntity<Integer> followUser(@RequestBody FollowingUserRequestDto dto){
        return  ResponseEntity.ok(userService.followUser(dto));
    }

    @PostMapping("/acceptfollower")
    @CrossOrigin("*")
    public  ResponseEntity<Integer> acceptFollower(@RequestBody AcceptFollowerRequestDto dto){
        return  ResponseEntity.ok(userService.acceptFollower(dto));
    }

//    @PostMapping("/removefollower")
//    @CrossOrigin("*")
//    public  ResponseEntity<Integer> removeFollower(@RequestBody AcceptFollowerRequestDto dto){
//        return  ResponseEntity.ok(userService.removeFollower(dto));
//    }

}
