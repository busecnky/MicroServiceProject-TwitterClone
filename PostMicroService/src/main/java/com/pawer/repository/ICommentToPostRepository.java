package com.pawer.repository;

import com.pawer.repository.entity.CommentToPost;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICommentToPostRepository extends MongoRepository<CommentToPost,String> {
    Optional<List<CommentToPost>> findByPostId(String postId);
}
