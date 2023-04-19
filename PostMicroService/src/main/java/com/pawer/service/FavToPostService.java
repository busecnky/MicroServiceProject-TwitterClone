package com.pawer.service;

import com.pawer.repository.IFavPostRepository;
import com.pawer.repository.entity.FavToPost;
import com.pawer.utility.ServiceManagerImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavToPostService extends ServiceManagerImpl<FavToPost,String> {
    private final IFavPostRepository favPostRepository;

    public FavToPostService(IFavPostRepository favPostRepository) {
        super(favPostRepository);
        this.favPostRepository = favPostRepository;
    }

    public Boolean findFavToPostBoolean(String postId,Long userId){
        Optional<FavToPost> favToPost=  favPostRepository.findOptionalByPostIdAndUserId(postId,userId);
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

    public Optional<FavToPost> findFavToPost(String postId, Long userId){
        return favPostRepository.findOptionalByPostIdAndUserId(postId,userId);
    }

    public Optional<List<FavToPost>> findAllFavToPost(Long userId){
        return favPostRepository.findOptionalByUserId(userId);
    }
}
