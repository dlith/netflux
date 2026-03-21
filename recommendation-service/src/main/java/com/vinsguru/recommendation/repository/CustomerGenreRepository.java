package com.vinsguru.recommendation.repository;

import com.vinsguru.recommendation.entity.CustomerGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerGenreRepository extends JpaRepository<CustomerGenre, Integer> {
}
