package com.ukim.finki.domashna2.repository;

import com.ukim.finki.domashna2.model.WineryUserReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WineryUserReviewRepository extends JpaRepository<WineryUserReview, Long> {
    public List<WineryUserReview> getAllByWineryIdEquals(Long wineryId);

    @Query("SELECT r.rating FROM WineryUserReview r WHERE r.wineryId = :wineryId")
    List<Float> getAllRatingsByWineryId(@Param("wineryId") Long wineryId);
}

