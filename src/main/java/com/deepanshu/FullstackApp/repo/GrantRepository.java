package com.deepanshu.FullstackApp.repo;

import com.deepanshu.FullstackApp.model.Grant;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GrantRepository extends JpaRepository<Grant, Long> {

    @Query(value = "SELECT DISTINCT g.tags FROM Grant g", nativeQuery = true)
    List<String> findAllUniqueTags();

    @Query(value = "SELECT DISTINCT g.grant_type FROM Grant g", nativeQuery = true)
    List<String> findAllUniqueGrantType();

    @Query("SELECT g FROM Grant g WHERE (:tags IS NULL OR g.tags IN :tags) AND (:grantType IS NULL OR g.grantType = :grantType) AND (:searchQuery IS NULL OR (LOWER(g.nonprofitLegalName) LIKE %:searchQuery% OR LOWER(g.grantSubmissionName) LIKE %:searchQuery% OR LOWER(g.foundationOwner) LIKE %:searchQuery%))")
    Page<Grant> findAllByTagsContaining(@Param("tags") List<String> tags, @Param("searchQuery") String searchQuery, @Param("grantType") Grant.GrantType grantType, Pageable pageable);

}

