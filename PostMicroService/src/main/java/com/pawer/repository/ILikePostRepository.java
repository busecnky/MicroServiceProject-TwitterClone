package com.pawer.repository;

import com.pawer.repository.entity.LikeToPost;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ILikePostRepository extends MongoRepository<LikeToPost,String> {

    Optional<LikeToPost> findOptionalByPostIdAndUserId(String postId, Long userId);
}
