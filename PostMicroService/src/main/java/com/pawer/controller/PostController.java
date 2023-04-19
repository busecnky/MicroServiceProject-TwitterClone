package com.pawer.controller;


import com.pawer.dto.request.CommentToPostDto;
import com.pawer.dto.request.FawPostRequestDto;
import com.pawer.dto.request.BaseRequestDto;
import com.pawer.dto.request.LikePostRequestDto;
import com.pawer.dto.response.BaseResponseDto;
import com.pawer.dto.response.CommentToPostResponse;
import com.pawer.dto.response.MyFawPostListResponseDto;
import com.pawer.dto.response.PostFindAllResponse;
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
    public ResponseEntity<Page<PostFindAllResponse>> findallPage(@RequestBody BaseResponseDto dto,
                                                                 @RequestParam(defaultValue = "10")Integer pageSize,
                                                                 @RequestParam(defaultValue = "0") int pageNumber,
                                                                 @RequestParam(defaultValue = "DESC") Sort.Direction direction,
                                                                 @RequestParam(defaultValue = "createDate") String sortParameter){


        return ResponseEntity.ok(postService.findAllPosts(dto.getToken(),pageSize,pageNumber,direction,sortParameter));
    }
    /*
    @GetMapping("/findallpage")
    @CrossOrigin("*")
    public ResponseEntity<Page<Post>> findallPage(@RequestParam(defaultValue = "10")Integer pageSize,
                                                  @RequestParam(defaultValue = "0")   int pageNumber,
                                                  @RequestParam(defaultValue = "DESC") Sort.Direction direction,
                                                  @RequestParam(defaultValue = "createDate") String sortParameter){

        return ResponseEntity.ok(postService.findAll(pageSize,pageNumber,direction,sortParameter));
    }

*/
    @PostMapping("/findallpageplus")
    @CrossOrigin("*")
    public ResponseEntity<Page<Post>> findallPagePlus(@RequestParam(defaultValue = "10")Integer pageSize,
                                                      @RequestParam(defaultValue = "0")  Integer pageNumber,
                                                      @RequestParam(defaultValue = "ASC") Sort.Direction direction,
                                                      @RequestParam(defaultValue = "createDate") String sortParameter){
        return ResponseEntity.ok(postService.findAllPlus(pageSize,pageNumber,direction,sortParameter));

    }

    @GetMapping("/findallpageminus")
    @CrossOrigin("*")
    public ResponseEntity<Page<Post>> findallPageMinus(@RequestParam(defaultValue = "10")Integer pageSize,
                                                      @RequestParam(defaultValue = "0")  Integer pageNumber,
                                                      @RequestParam(defaultValue = "ASC") Sort.Direction direction,
                                                      @RequestParam(defaultValue = "createDate") String sortParameter){
        return ResponseEntity.ok(postService.findAllMinus(pageSize,pageNumber,direction,sortParameter));

    }

    @PostMapping ("/findallmypost")
    @CrossOrigin("*")
    public ResponseEntity<Page<Post>> findMyPosts(@RequestBody BaseResponseDto dto,
                                                  @RequestParam(defaultValue = "10")Integer pageSize,
                                                  @RequestParam(defaultValue = "0") int pageNumber,
                                                  @RequestParam(defaultValue = "DESC") Sort.Direction direction,
                                                  @RequestParam(defaultValue = "createDate") String sortParameter){
        return ResponseEntity.ok(postService.myPost(dto.getToken(),pageSize,pageNumber,direction,sortParameter));
    }

    @GetMapping("/findallcomment")
    @CrossOrigin("*")
    @ResponseBody
    public ResponseEntity<List<CommentToPostResponse>> getCommentList(CommentToPostDto dto){
        return ResponseEntity.ok(postService.findAllComment(dto));
    }

    @PostMapping("/likepost")
    @CrossOrigin("*")
    @ResponseBody
    public ResponseEntity<Integer> getLikeCount(LikePostRequestDto dto){
        return ResponseEntity.ok(postService.likePostCount(dto));
    }


    @GetMapping("/mylikes")
    @CrossOrigin("*")
    @ResponseBody
    public ResponseEntity<List<Post>> findAllMyLikesList(BaseRequestDto dto){
        return ResponseEntity.ok(postService.findAllMyLikesList(dto));
    }

    @PostMapping("/cratefawpost")
    @CrossOrigin("*")
    public ResponseEntity<Boolean> createLikePost(@RequestBody FawPostRequestDto dto){
        return ResponseEntity.ok(postService.createFawPost(dto));
    }

    @PostMapping("/myfawtopowlist")
    @CrossOrigin("*")
    public ResponseEntity<List<MyFawPostListResponseDto>> myFawPostList(@RequestBody BaseRequestDto dto){
        return ResponseEntity.ok(postService.myFawPostList(dto));
    }

    // public PageRequest next() {
    //    return new PageRequest(getPageNumber() + 1, getPageSize(), getSort());
    //}


//
//    public PageRequest previous() {
//        return getPageNumber() == 0 ? this : new PageRequest(getPageNumber() - 1, getPageSize(), getSort());

}







