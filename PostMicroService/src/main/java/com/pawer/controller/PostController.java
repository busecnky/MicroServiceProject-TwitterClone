package com.pawer.controller;


import com.pawer.dto.request.BaseRequestDto;
import com.pawer.dto.request.CommentToPostRequestDto;
import com.pawer.dto.response.BaseResponseDto;
import com.pawer.dto.response.CommentToPostResponse;
import com.pawer.dto.response.PostFindAllResponse;
import com.pawer.repository.entity.Post;
import com.pawer.service.CommentToPostService;
import com.pawer.service.FavToPostService;
import com.pawer.service.LikeToPostService;
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
    private final LikeToPostService likeToPostService;
    private final CommentToPostService commentToPostService;
    private final FavToPostService favToPostService;




    @PostMapping("/findallpage")
    @CrossOrigin("*")
    public ResponseEntity<Page<PostFindAllResponse>> findallPage(@RequestBody BaseResponseDto dto,
                                                                 @RequestParam(defaultValue = "10")Integer pageSize,
                                                                 @RequestParam(defaultValue = "0") int pageNumber,
                                                                 @RequestParam(defaultValue = "DESC") Sort.Direction direction,
                                                                 @RequestParam(defaultValue = "createDate") String sortParameter){
        try {
            /**
             * sayfa yüklenirken
             * kullanicinin takip ettiklerinin id listesi gerekiyor
             * bu da rabbit den geliyor ama rabbit yavaş kalıyor bu yüzden hata veriyor
             * bu hatanın çözümü olarak thread sleep atıldı ve 1sn verildi.
             *
             */
            Thread.sleep(300);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        return ResponseEntity.ok(postService.findAllPosts(dto.getToken(),pageSize,pageNumber,direction,sortParameter));
    }
    @PostMapping("/discoverpage")
    @CrossOrigin("*")
    public ResponseEntity<Page<PostFindAllResponse>> discover(@RequestBody BaseResponseDto dto,
                                                                 @RequestParam(defaultValue = "10")Integer pageSize,
                                                                 @RequestParam(defaultValue = "0") int pageNumber,
                                                                 @RequestParam(defaultValue = "DESC") Sort.Direction direction,
                                                                 @RequestParam(defaultValue = "createDate") String sortParameter){

        try {
            System.out.println("thread sleep bekleniyor");
            Thread.sleep(300);
            System.out.println("thread sleep bekleme bitti");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("thread beklemedi");
        return ResponseEntity.ok(postService.discoverPage(dto.getToken(),pageSize,pageNumber,direction,sortParameter));
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

    @PostMapping("/createlikepost")
    @CrossOrigin("*")
    public ResponseEntity<Boolean> createLikePost(@RequestBody BaseRequestDto dto){
        System.out.println("create like post a geldi");
        likeToPostService.createLikePost(dto);
        return ResponseEntity.ok(true);
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
    public ResponseEntity<List<CommentToPostResponse>> getCommentList(BaseRequestDto dto){
        return ResponseEntity.ok(commentToPostService.findAllComment(dto));
    }

    @PostMapping("/likepost")
    @CrossOrigin("*")
    @ResponseBody
    public ResponseEntity<Integer> getLikeCount(BaseRequestDto dto){
        return ResponseEntity.ok(likeToPostService.likePostCount(dto));
    }



    @PostMapping("/createfavpost")
    @CrossOrigin("*")
    public ResponseEntity<Boolean> createFavPost(@RequestBody BaseRequestDto dto){
        return ResponseEntity.ok(favToPostService.createFavPost(dto));
    }

    @PostMapping("/myfavtopostlist")
    @CrossOrigin("*")
    public ResponseEntity<List<PostFindAllResponse>> myFavPostList(@RequestBody BaseRequestDto dto){
        return ResponseEntity.ok(favToPostService.myFavPostList(dto));
    }

    @PostMapping("/createcommenttopost")
    @CrossOrigin("*")
    public ResponseEntity<Boolean> createCommentToPost(@RequestBody CommentToPostRequestDto dto){
        System.out.println("create comment post a geldi");
        commentToPostService.createCommentToPost(dto);
        return ResponseEntity.ok(true);
    }




}







