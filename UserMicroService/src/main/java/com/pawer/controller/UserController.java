package com.pawer.controller;


import com.pawer.dto.request.*;
import com.pawer.dto.response.FindByIdResponseDto;
import com.pawer.exception.EErrorType;
import com.pawer.exception.UserException;
import com.pawer.repository.entity.User;
import com.pawer.service.FollowService;
import com.pawer.service.FollowerService;
import com.pawer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

        return ResponseEntity.ok(userService.createCommentToPost(dto));
    }

    @PostMapping("/likepost")
    @CrossOrigin("*")
    public ResponseEntity<Boolean> likePost(@RequestBody LikePostRequestDto dto){
        return ResponseEntity.ok(userService.likePost(dto));
    }

    @PostMapping("/follow")
    @CrossOrigin("*")
    public  ResponseEntity<Integer> followUser(@RequestBody FollowingUserRequestDto dto){
        return  ResponseEntity.ok(followService.followUser(dto));
    }

    @PostMapping("/acceptfollower")
    @CrossOrigin("*")
    public  ResponseEntity<Integer> acceptFollower(@RequestBody AcceptFollowerRequestDto dto){
        return  ResponseEntity.ok(followerService.acceptFollower(dto));
    }

    @GetMapping("/findalluser")
    @ResponseBody
    @CrossOrigin("*")
    public ResponseEntity<List<User>> findAllUser(){
        return ResponseEntity.ok(userService.findAll());
    }





//    @CrossOrigin("*")
//    @PostMapping("/resim")
//    public void yukle(@RequestParam("resim") MultipartFile dosya) throws IOException {
//        byte [] byteresim;
//        try {
//            byteresim= dosya.getBytes();
//            userService.resimkaydet(byteresim);
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        System.out.println("Dosya yüklendi: " + dosya.getOriginalFilename());
//        System.out.println("direkt---> "+byteresim);
//        System.out.println("tostrint ile --> "+byteresim.toString());
//    }


//    @PostMapping("/removefollower")
//    @CrossOrigin("*")
//    public  ResponseEntity<Integer> removeFollower(@RequestBody AcceptFollowerRequestDto dto){
//        return  ResponseEntity.ok(userService.removeFollower(dto));
//    }

}
