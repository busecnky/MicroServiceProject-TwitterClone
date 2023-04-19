package com.pawer.service;

import com.pawer.repository.IFawPostRepository;
import com.pawer.repository.entity.FawToPost;
import com.pawer.utility.ServiceManagerImpl;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FawToPostService extends ServiceManagerImpl<FawToPost,String> {
    private final IFawPostRepository fawPostRepository;

    public FawToPostService(IFawPostRepository fawPostRepository) {
        super(fawPostRepository);
        this.fawPostRepository = fawPostRepository;
    }

    public Boolean findFawToPostBoolean(String postId,Long userId){
        Optional<FawToPost> fawToPost=  fawPostRepository.findOptionalByPostIdAndUserId(postId,userId);
        if (fawToPost.isPresent()){
            if (fawToPost.get().getStatement()==true){
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }

    public Optional<FawToPost> findFawToPost(String postId,Long userId){
        return fawPostRepository.findOptionalByPostIdAndUserId(postId,userId);
    }

    public Optional<List<FawToPost>> findAllFawToPost(Long userId){
        return fawPostRepository.findOptionalByUserId(userId);
    }
}
