package com.pawer.repository;

import com.pawer.repository.entity.Follower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface IFollowerRepository extends JpaRepository<Follower,Long> {

    Optional<Follower> findOptionalByUserIdAndFollowerId(Long userId, Long followerId);

}
