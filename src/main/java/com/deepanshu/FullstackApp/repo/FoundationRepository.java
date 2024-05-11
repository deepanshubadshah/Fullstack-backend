package com.deepanshu.FullstackApp.repo;

import com.deepanshu.FullstackApp.model.Foundation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoundationRepository extends JpaRepository<Foundation, Long> {

}
