package com.deepanshu.FullstackApp.repo;

import com.deepanshu.FullstackApp.model.Grant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrantRepository extends JpaRepository<Grant, Long> {
}


