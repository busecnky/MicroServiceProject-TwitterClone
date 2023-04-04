package com.pawer.controller;


import com.pawer.dto.request.CommentToPostDto;
import com.pawer.dto.response.BaseResponseDto;
import com.pawer.repository.entity.CommentToPost;
import com.pawer.repository.entity.Post;
import com.pawer.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;




    @PostMapping("/findallpage")
    @CrossOrigin("*")
    public ResponseEntity<Page<Post>> findallPage(@RequestParam(defaultValue = "10")Integer pageSize,
                                                  @RequestParam(defaultValue = "0")   int pageNumber,
                                                  @RequestParam(defaultValue = "DESC") Sort.Direction direction,
                                                  @RequestParam(defaultValue = "createDate") String sortParameter){

        return ResponseEntity.ok(postService.findAll(pageSize,pageNumber,direction,sortParameter));
    }



    @PostMapping("/findallpageplus")
    @CrossOrigin("*")
    public ResponseEntity<Page<Post>> findallPagePlus(@RequestParam(defaultValue = "10")Integer pageSize,
                                                      @RequestParam(defaultValue = "0")  Integer pageNumber,
                                                      @RequestParam(defaultValue = "ASC") Sort.Direction direction,
                                                      @RequestParam(defaultValue = "createDate") String sortParameter){
        return ResponseEntity.ok(postService.findAllPlus(pageSize,pageNumber,direction,sortParameter));

    }

    @PostMapping("/findallpageminus")
    @CrossOrigin("*")
    public ResponseEntity<Page<Post>> findallPageMinus(@RequestParam(defaultValue = "10")Integer pageSize,
                                                      @RequestParam(defaultValue = "0")  Integer pageNumber,
                                                      @RequestParam(defaultValue = "ASC") Sort.Direction direction,
                                                      @RequestParam(defaultValue = "createDate") String sortParameter){
        return ResponseEntity.ok(postService.findAllMinus(pageSize,pageNumber,direction,sortParameter));

    }

    @PostMapping ("/findallmypost")
    @CrossOrigin("*")
    public ResponseEntity<Page<Post>> findByToken(@RequestBody BaseResponseDto dto,
                                                             @RequestParam(defaultValue = "10")Integer pageSize,
                                                             @RequestParam(defaultValue = "0") int pageNumber,
                                                             @RequestParam(defaultValue = "DESC") Sort.Direction direction,
                                                             @RequestParam(defaultValue = "createDate") String sortParameter){
        System.out.println("----------" + dto.getToken());
        return ResponseEntity.ok(postService.findByToken(dto.getToken(),pageSize,pageNumber,direction,sortParameter));
    }

    @GetMapping("/findallcomment")
    @CrossOrigin("*")
    public ResponseEntity<List<CommentToPost>> getCommentList(@RequestBody CommentToPostDto dto){
        return ResponseEntity.ok(postService.findAllComment(dto));
    }




    // public PageRequest next() {
    //    return new PageRequest(getPageNumber() + 1, getPageSize(), getSort());
    //}


//
//    public PageRequest previous() {
//        return getPageNumber() == 0 ? this : new PageRequest(getPageNumber() - 1, getPageSize(), getSort());

}







