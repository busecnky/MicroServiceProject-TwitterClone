package com.pawer.service;

import com.pawer.dto.request.BaseRequestDto;
import com.pawer.dto.request.CommentToPostRequestDto;
import com.pawer.dto.response.CommentToPostResponse;
import com.pawer.manager.IElasticServiceManager;
import com.pawer.repository.ICommentToPostRepository;
import com.pawer.repository.entity.CommentToPost;
import com.pawer.utility.JwtTokenManager;
import com.pawer.utility.ServiceManagerImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentToPostService extends ServiceManagerImpl<CommentToPost,String> {
    private final ICommentToPostRepository commentToPostRepository;
    private final JwtTokenManager jwtTokenManager;
    private final IElasticServiceManager elasticServiceManager;


    public CommentToPostService(ICommentToPostRepository commentToPostRepository, JwtTokenManager jwtTokenManager, IElasticServiceManager elasticServiceManager) {
        super(commentToPostRepository);
        this.commentToPostRepository = commentToPostRepository;
        this.jwtTokenManager = jwtTokenManager;
        this.elasticServiceManager = elasticServiceManager;
    }

    public void createCommentToPost(CommentToPostRequestDto dto){
        CommentToPost commentToPost= new CommentToPost();
        Long userId = jwtTokenManager.validToken(dto.getToken()).get();
        commentToPost.setComment(dto.getComment());
        commentToPost.setUserId(userId);
        commentToPost.setPostId(dto.getPostId());
        commentToPostRepository.save(commentToPost);
        elasticServiceManager.createCommentToPost(dto);
    }

    public List<CommentToPostResponse> findAllComment(BaseRequestDto dto){
        List<CommentToPostResponse> comments= new ArrayList<>();
        for (CommentToPost comment: commentToPostRepository.findByPostId(dto.getPostId()).get()){
            CommentToPostResponse commentToPostResponse=new CommentToPostResponse();
            commentToPostResponse.setComment(comment.getComment());
            commentToPostResponse.setPostId(comment.getPostId());
            commentToPostResponse.setUserId(String.valueOf(comment.getUserId()));
            comments.add(commentToPostResponse);
        }
        return comments;
    }


}
