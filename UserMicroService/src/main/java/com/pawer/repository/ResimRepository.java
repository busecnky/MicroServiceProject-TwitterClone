package com.pawer.repository;

import com.pawer.repository.entity.Resim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResimRepository extends JpaRepository<Resim,Long> {
}
