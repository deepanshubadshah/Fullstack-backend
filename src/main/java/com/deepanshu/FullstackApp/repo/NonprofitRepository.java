package com.deepanshu.FullstackApp.repo;

import com.deepanshu.FullstackApp.model.Nonprofit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NonprofitRepository extends JpaRepository<Nonprofit, Long> {

}
