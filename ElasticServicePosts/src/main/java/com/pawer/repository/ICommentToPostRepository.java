package com.pawer.repository;

import com.pawer.repository.entity.CommentToPost;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICommentToPostRepository extends ElasticsearchRepository<CommentToPost,String> {
    Optional<List<CommentToPost>> findByPostId(String postId);
}
