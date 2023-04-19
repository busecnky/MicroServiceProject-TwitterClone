package com.pawer.repository;

import com.pawer.repository.entity.FawToPost;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IFawPostRepository extends MongoRepository<FawToPost,String> {
    Optional<FawToPost> findOptionalByPostIdAndUserId(String postId, Long userId);
    Optional<List<FawToPost>> findOptionalByUserId(Long userId);

}
