package com.pawer.controller;


import com.pawer.dto.request.CreatePostDto;
import com.pawer.dto.request.FindByIdRequestDto;
import com.pawer.dto.request.UpdateUserProfileRequestDto;
import com.pawer.dto.response.FindByIdResponseDto;
import com.pawer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/check")
    public  ResponseEntity<String> getList(){
        return ResponseEntity.ok().body("User Service is Working...");


    }
    @PostMapping("/createPost")
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

}
