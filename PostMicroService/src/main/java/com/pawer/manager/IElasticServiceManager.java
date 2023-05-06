package com.pawer.manager;

import com.pawer.dto.request.BaseRequestDto;
import com.pawer.dto.request.CommentToPostRequestDto;
import com.pawer.dto.request.PostSaveRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "post-service-feign",
        url = "http://localhost:9999/post",
        decode404 = true
)
public interface IElasticServiceManager {

    @PostMapping("/getalldata")
    ResponseEntity<Void> getAllData(@RequestBody PostSaveRequestDto dto);

    @PostMapping("/createlikepost")
    @CrossOrigin("*")
    ResponseEntity<Void> createLikePost(@RequestBody BaseRequestDto dto);

    @PostMapping("/createfavpost")
    @CrossOrigin("*")
    ResponseEntity<Boolean> createFavPost(@RequestBody BaseRequestDto dto);

    @PostMapping("/createcommenttopost")
    @CrossOrigin("*")
    ResponseEntity<Void> createCommentToPost(@RequestBody CommentToPostRequestDto dto);

}
