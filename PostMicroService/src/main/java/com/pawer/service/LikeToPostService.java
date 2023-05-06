package com.pawer.service;

import com.pawer.dto.request.BaseRequestDto;
import com.pawer.exception.EErrorType;
import com.pawer.exception.PostException;
import com.pawer.manager.IElasticServiceManager;
import com.pawer.repository.ILikePostRepository;
import com.pawer.repository.entity.LikeToPost;
import com.pawer.repository.entity.Post;
import com.pawer.utility.JwtTokenManager;
import com.pawer.utility.ServiceManagerImpl;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeToPostService extends ServiceManagerImpl<LikeToPost, String> {
    private final ILikePostRepository likePostRepository;
    private final JwtTokenManager jwtTokenManager;
    private final PostService postService;
    private final IElasticServiceManager elasticServiceManager;

    public LikeToPostService(ILikePostRepository likePostRepository, JwtTokenManager jwtTokenManager, @Lazy PostService postService, IElasticServiceManager elasticServiceManager) {
        super(likePostRepository);
        this.likePostRepository = likePostRepository;
        this.jwtTokenManager = jwtTokenManager;
        this.postService = postService;
        this.elasticServiceManager = elasticServiceManager;
    }

    public Boolean findByPostIdAndUserIdBoolean(String postId, Long userId) {
        Optional<LikeToPost> likeToPost = likePostRepository.findOptionalByPostIdAndUserId(postId, userId);

        if (likeToPost.isPresent()) {
            if (likeToPost.get().getStatement() == true) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public void createLikePost(BaseRequestDto model) {
        Long modelUserId = jwtTokenManager.validToken(model.getToken()).get();
        Optional<LikeToPost> likeToPost = likePostRepository.findOptionalByPostIdAndUserId(model.getPostId(), modelUserId);
        Optional<Post> post = postService.findById(model.getPostId());

        if (likeToPost.isPresent()){
            if(model.getStatement() == true){
                post.get().setLikeCount(post.get().getLikeCount()+1);
                postService.save(post.get());
            }else {
                post.get().setLikeCount(post.get().getLikeCount()-1);
                postService.save(post.get());
            }
            likeToPost.get().setStatement(model.getStatement());
            save(likeToPost.get());
        }else {
            save(LikeToPost.builder()
                    .postId(model.getPostId())
                    .userId(modelUserId)
                    .statement(model.getStatement())
                    .build());
            post.get().setLikeCount(post.get().getLikeCount()+1);
            postService.save(post.get());
        }
        elasticServiceManager.createLikePost(model);
    }

    public Integer likePostCount(BaseRequestDto dto) {
        if (dto.getToken() == null || dto.getToken() == "") {
            throw new PostException(EErrorType.INVALID_TOKEN);
        }
        Optional<Post> post = postService.findById(dto.getPostId());
        return post.get().getLikeCount();
    }
}
