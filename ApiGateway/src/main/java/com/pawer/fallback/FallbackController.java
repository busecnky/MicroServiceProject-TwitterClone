package com.pawer.fallback;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class FallbackController {

    @GetMapping("/fallbackauth")
    public ResponseEntity<String> fallbackauth(){

        return ResponseEntity.ok("Auth service is temporarily disabled please try again later.");
    }

    @GetMapping("/fallbackpost")
    public ResponseEntity<String> fallbackpost(){

        return ResponseEntity.ok("Post service is temporarily disabled please try again later.");
    }

    @GetMapping("/fallbackuser")
    public ResponseEntity<String> fallbackuser(){

        return ResponseEntity.ok("User service is temporarily disabled please try again later.");
    }
}
