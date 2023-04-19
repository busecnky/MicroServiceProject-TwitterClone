package com.pawer.service;

import com.pawer.repository.ILikePostRepository;
import com.pawer.repository.entity.LikeToPost;
import com.pawer.utility.ServiceManagerImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LikeToPostService extends ServiceManagerImpl<LikeToPost, String> {
    private final ILikePostRepository likePostRepository;

    public LikeToPostService(ILikePostRepository likePostRepository) {
        super(likePostRepository);
        this.likePostRepository = likePostRepository;
    }

    public Optional<List<LikeToPost>> findByUserIdList(Long userId){
        return likePostRepository.findOptionalByUserId(userId);
    }

    public  Optional<LikeToPost> findByPostIdAndUserId(String postId, Long userId) {
       return likePostRepository.findOptionalByPostIdAndUserId(postId,userId);
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




}
