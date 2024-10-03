package com.farmstory.repository;

import com.farmstory.entity.Terms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TermsRepository extends JpaRepository<Terms, Integer> {
    @Query("select t from Terms t")
    Terms findterms();
}
