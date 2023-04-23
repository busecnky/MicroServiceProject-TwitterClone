package com.pawer.repository;

import com.pawer.repository.entity.LikeToPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ILikePostRepository extends ElasticsearchRepository<LikeToPost,String> {
    Optional<List<LikeToPost>> findOptionalByUserId(Long userId);

    Optional<Page<LikeToPost>> findOptionalByUserId(Long userId, Pageable pageable);

    Optional<List<LikeToPost>> findOptionalByPostId(String postId);

    Optional<LikeToPost> findOptionalByPostIdAndUserId(String postId, Long userId);
}
