package com.pawer.repository;

import com.pawer.repository.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAuthRepository extends JpaRepository<Auth,Long> {
    Optional<Auth> findOptionalByUsernameAndPassword(String username, String password);
    @Query(value = " select COUNT(*)>0 from tblauth as a where a.username=?1 ", nativeQuery = true)
    Boolean isUser(String user);
    Optional<Auth> findByUsername(String username);

}
