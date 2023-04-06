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

        return ResponseEntity.ok("Auth servis geçici bir süre devre dışıdır lütfen daha sonra tekrar deneyin");
    }

    @GetMapping("/fallbackpost")
    public ResponseEntity<String> fallbackpost(){

        return ResponseEntity.ok("Post servis geçici bir süre devre dışıdır lütfen daha sonra tekrar deneyin");
    }

    @GetMapping("/fallbackuser")
    public ResponseEntity<String> fallbackuser(){

        return ResponseEntity.ok("User servis geçici bir süre devre dışıdır lütfen daha sonra tekrar deneyin");
    }
}
