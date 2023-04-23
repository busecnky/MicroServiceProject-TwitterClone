package com.pawer.repository;

import com.pawer.repository.entity.FavToPost;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IFavPostRepository extends ElasticsearchRepository<FavToPost,String> {
    Optional<FavToPost> findOptionalByPostIdAndUserId(String postId, Long userId);
    Optional<List<FavToPost>> findOptionalByUserId(Long userId);

}
