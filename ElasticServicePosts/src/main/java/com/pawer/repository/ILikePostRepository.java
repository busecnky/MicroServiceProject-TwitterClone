package com.pawer.repository;

import com.pawer.repository.entity.LikeToPost;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ILikePostRepository extends ElasticsearchRepository<LikeToPost,String> {
    Optional<LikeToPost> findOptionalByPostIdAndUserId(String postId, Long userId);
}
