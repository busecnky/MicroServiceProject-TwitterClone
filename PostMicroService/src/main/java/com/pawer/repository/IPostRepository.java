package com.pawer.repository;

import com.pawer.repository.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface IPostRepository extends MongoRepository<Post,String> {

    Page<Post> findByUserId(Long id,Pageable pageable);
    Optional<List<Post>> findOptionalByUserId (Long id);



}
