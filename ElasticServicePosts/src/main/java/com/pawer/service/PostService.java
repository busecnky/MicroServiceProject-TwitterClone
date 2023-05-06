package com.pawer.service;

import com.pawer.dto.request.PostSaveRequestDto;
import com.pawer.dto.response.PostFindAllResponse;
import com.pawer.exception.EErrorType;
import com.pawer.exception.PostException;
import com.pawer.mapper.IPostMapper;
import com.pawer.rabbitmq.messagemodel.ModelCreatePost;
import com.pawer.repository.IPostRepository;
import com.pawer.repository.entity.Post;
import com.pawer.utility.JwtTokenManager;
import com.pawer.utility.ServiceManagerImpl;
import com.pawer.utility.StaticValues;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService extends ServiceManagerImpl<Post,String> {

    private final IPostRepository postrepository;
    private final JwtTokenManager jwtTokenManager;
    private final FavToPostService favToPostService;
    private final LikeToPostService likeToPostService;

    public PostService(IPostRepository postrepository, JwtTokenManager jwtTokenManager
            , @Lazy FavToPostService favToPostService,@Lazy  LikeToPostService likeToPostService) {
        super(postrepository);
        this.postrepository = postrepository;
        this.jwtTokenManager = jwtTokenManager;
        this.favToPostService = favToPostService;
        this.likeToPostService = likeToPostService;
    }
    public void getAllDataFromPost(PostSaveRequestDto dto) {
        postrepository.save(IPostMapper.INSTANCE.toPost(dto));
    }

    public void savePost(ModelCreatePost modelCreatePost){
        Long id = jwtTokenManager.validToken(modelCreatePost.getToken()).get();
        Post post = Post.builder()
                .userId(id)
                .content(modelCreatePost.getContent())
                .name(modelCreatePost.getName())
                .username(modelCreatePost.getUsername())
                .likeCount(0)
              .build();
        save(post);
    }

    public Page<PostFindAllResponse> findAllPosts(String token,
                                                  Integer pageSize,
                                                  int currentPage, Sort.Direction direction,
                                                  String sortingParameter){
        if (token == null || token == "") {
            throw new PostException(EErrorType.INVALID_TOKEN);
        }
        Long userId = jwtTokenManager.validToken(token).get();
        Pageable pageable = PageRequest.of(currentPage,  pageSize ,Sort.by(direction, sortingParameter) );
        List<PostFindAllResponse> postFindAllResponses = new ArrayList<>();

        for(Post post: homePagePosts()){
            PostFindAllResponse postFindAllResponse = new PostFindAllResponse();
            postFindAllResponse.setId(post.getId());
            postFindAllResponse.setUserId(post.getUserId());
            postFindAllResponse.setUsername(post.getUsername());
            postFindAllResponse.setName(post.getName());
            postFindAllResponse.setSurname(post.getSurname());
            postFindAllResponse.setContent(post.getContent());
            postFindAllResponse.setUrl(post.getUrl());
            postFindAllResponse.setLikeCount(post.getLikeCount());
            postFindAllResponse.setDate(post.getDate());
            postFindAllResponse.setTime(post.getTime());
            postFindAllResponse.setIsFav(favToPostService.findFavToPostBoolean(post.getId(),userId));
            postFindAllResponse.setIsLiked(likeToPostService.findByPostIdAndUserIdBoolean(post.getId(), userId));
            postFindAllResponses.add(postFindAllResponse);
        }
        Page<PostFindAllResponse> myPage = new PageImpl<>(postFindAllResponses, pageable, postFindAllResponses.size());

        return myPage;
    }

    public  Page<Post> myPost(String token,
                              Integer pageSize,
                              int currentPage, Sort.Direction direction,
                              String sortingParameter){
        Long userid = jwtTokenManager.validToken(token).get();
        Pageable pageable = PageRequest.of(currentPage,  pageSize ,Sort.by(direction, sortingParameter) );
        return postrepository.findByUserId(userid,pageable);
    }

    public List<Post> homePagePosts(){

        List<Post> posts = new ArrayList<>();
        for (Long folloId: StaticValues.modelFollowId.getFollowId()){
            Optional<List<Post>> posts1= postrepository.findOptionalByUserId(folloId);
            for (Post post : posts1.get()){
                posts.add(post);
            }
        }
        return posts;
    }

    public List<Post> discover( ){
        List<Post> posts = new ArrayList<>();
        for (Long folloId: StaticValues.modelFollowId.getFollowId()){
            Optional<List<Post>> posts1= postrepository.findOptionalByUserId(folloId);
            for (Post post : posts1.get()){
                posts.add(post);
            }
        }
        List<Post> findallPosts= new ArrayList<>();
        findAll().forEach(findallPosts::add);
        findallPosts.removeAll(posts);
        return findallPosts;
    }

    public Page<PostFindAllResponse> discoverPage(String token,
                                                  Integer pageSize,
                                                  int currentPage, Sort.Direction direction,
                                                  String sortingParameter){
        if (token == null || token == "") {
            throw new PostException(EErrorType.INVALID_TOKEN);
        }
        Long userId = jwtTokenManager.validToken(token).get();
        Pageable pageable = PageRequest.of(currentPage,  pageSize ,Sort.by(direction, sortingParameter) );
        List<PostFindAllResponse> postFindAllResponses = new ArrayList<>();

        for(Post post: discover()){
            if (post.getUserId()!=userId){

            PostFindAllResponse postFindAllResponse = new PostFindAllResponse();
            postFindAllResponse.setId(post.getId());
            postFindAllResponse.setUserId(post.getUserId());
            postFindAllResponse.setUsername(post.getUsername());
            postFindAllResponse.setName(post.getName());
            postFindAllResponse.setSurname(post.getSurname());
            postFindAllResponse.setContent(post.getContent());
            postFindAllResponse.setUrl(post.getUrl());
            postFindAllResponse.setLikeCount(post.getLikeCount());
            postFindAllResponse.setDate(post.getDate());
            postFindAllResponse.setTime(post.getTime());
            postFindAllResponse.setIsFav(favToPostService.findFavToPostBoolean(post.getId(),userId));
            postFindAllResponse.setIsLiked(likeToPostService.findByPostIdAndUserIdBoolean(post.getId(), userId));
            postFindAllResponses.add(postFindAllResponse);
            }
        }
        Page<PostFindAllResponse> myPage = new PageImpl<>(postFindAllResponses, pageable, postFindAllResponses.size());

        return myPage;
    }
}
