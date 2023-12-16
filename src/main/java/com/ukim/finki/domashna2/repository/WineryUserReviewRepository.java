package com.ukim.finki.domashna2.repository;

import com.ukim.finki.domashna2.model.WineryUserReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WineryUserReviewRepository extends JpaRepository<WineryUserReview, Long> {
    public List<WineryUserReview> getAllByLanguageEquals(String language);
}

