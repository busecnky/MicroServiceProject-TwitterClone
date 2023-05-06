package com.pawer.service;

import com.pawer.dto.request.BaseRequestDto;
import com.pawer.dto.response.PostFindAllResponse;
import com.pawer.repository.IFavPostRepository;
import com.pawer.repository.entity.FavToPost;
import com.pawer.repository.entity.Post;
import com.pawer.utility.JwtTokenManager;
import com.pawer.utility.ServiceManagerImpl;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FavToPostService extends ServiceManagerImpl<FavToPost,String> {
    private final IFavPostRepository favPostRepository;
    private final JwtTokenManager jwtTokenManager;
    private final PostService postService;
    private final LikeToPostService likeToPostService;

    public FavToPostService(IFavPostRepository favPostRepository, JwtTokenManager jwtTokenManager, @Lazy PostService postService,@Lazy  LikeToPostService likeToPostService) {
        super(favPostRepository);
        this.favPostRepository = favPostRepository;
        this.jwtTokenManager = jwtTokenManager;
        this.postService = postService;
        this.likeToPostService = likeToPostService;
    }

    public Boolean findFavToPostBoolean(String postId,Long userId){
        Optional<FavToPost> favToPost = favPostRepository.findOptionalByPostIdAndUserId(postId,userId);
        if (favToPost.isPresent()){
            if (favToPost.get().getStatement()==true){
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }

    public Boolean createFavPost(BaseRequestDto dto) {
        Long userId= jwtTokenManager.validToken(dto.getToken()).get();
        Optional<FavToPost> favToPost= favPostRepository.findOptionalByPostIdAndUserId(dto.getPostId(), userId);
        if (favToPost.isPresent()){
            favToPost.get().setStatement(dto.getStatement());
            save(favToPost.get());
            return dto.getStatement();
        }else {
            save(FavToPost.builder()
                    .userId(userId)
                    .postId(dto.getPostId())
                    .statement(dto.getStatement())
                    .build());
            return dto.getStatement();
        }
    }

    public List<PostFindAllResponse> myFavPostList(BaseRequestDto dto){
        Long userId=jwtTokenManager.validToken(dto.getToken()).get();

        Optional<List<FavToPost>>  favToPosts= favPostRepository.findOptionalByUserId(userId);
        List<PostFindAllResponse> myFavPostListResponseDtos =new ArrayList<>();

        if (favToPosts.get()!=null){
            for (FavToPost favToPost : favToPosts.get()){
                if (favToPost.getStatement()==true){

                    PostFindAllResponse myFavPostListResponseDto= new PostFindAllResponse();
                    Post postt = postService.findById(favToPost.getPostId()).get();
                    myFavPostListResponseDto.setContent(postt.getContent());
                    myFavPostListResponseDto.setIsFav(favToPost.getStatement());
                    myFavPostListResponseDto.setName(postt.getName());
                    myFavPostListResponseDto.setDate(postt.getDate());
                    myFavPostListResponseDto.setTime(postt.getTime());
                    myFavPostListResponseDto.setUrl(postt.getUrl());
                    myFavPostListResponseDto.setSurname(postt.getSurname());
                    myFavPostListResponseDto.setUsername(postt.getUsername());
                    myFavPostListResponseDto.setId(postt.getId());
                    myFavPostListResponseDto.setIsLiked(likeToPostService.findByPostIdAndUserIdBoolean(postt.getId(),userId));
                    myFavPostListResponseDto.setLikeCount(postt.getLikeCount());
                    myFavPostListResponseDto.setUserId(postt.getUserId());
                    myFavPostListResponseDtos.add(myFavPostListResponseDto);
                }
            }
            return myFavPostListResponseDtos;
        }
        return myFavPostListResponseDtos;
    }
}
