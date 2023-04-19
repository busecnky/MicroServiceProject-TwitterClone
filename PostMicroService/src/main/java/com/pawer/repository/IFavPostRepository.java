package com.pawer.repository;

import com.pawer.repository.entity.FavToPost;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IFavPostRepository extends MongoRepository<FavToPost,String> {
    Optional<FavToPost> findOptionalByPostIdAndUserId(String postId, Long userId);
    Optional<List<FavToPost>> findOptionalByUserId(Long userId);

}
