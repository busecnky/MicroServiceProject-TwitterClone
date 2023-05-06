package com.pawer.controller;


import com.pawer.dto.request.AuthLoginDto;
import com.pawer.dto.request.AuthRegisterRequestDto;
import com.pawer.dto.request.ChangePasswordDto;
import com.pawer.dto.response.AuthLoginResponse;
import com.pawer.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;



@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    @CrossOrigin("*")
    public ResponseEntity<Boolean> register(@RequestBody @Valid AuthRegisterRequestDto dto) throws InterruptedException {
        return ResponseEntity.ok( authService.register(dto));

    }
    @PostMapping("/login")
    @CrossOrigin("*")
    public ResponseEntity<AuthLoginResponse> doLogin(@RequestBody AuthLoginDto dto){
        return ResponseEntity.ok((authService.doLogin(dto)));
    }

    @PostMapping("/changepassword")
    @CrossOrigin("*")
    public ResponseEntity<Boolean> changePassword(@RequestBody ChangePasswordDto dto){
        return ResponseEntity.ok(authService.changePassword(dto));
    }


}
