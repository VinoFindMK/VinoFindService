package com.ukim.finki.domashna2.service;

import com.google.maps.model.PlaceDetails;
import com.google.maps.model.PlacesSearchResult;
import com.ukim.finki.domashna2.model.WineryInfo;
import com.ukim.finki.domashna2.model.WineryReview;
import com.ukim.finki.domashna2.model.WineryUserReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WineryService {
    Page<WineryInfo> getAllWineries(Pageable pageable);

    Page<WineryInfo> searchWineries(String query, Pageable pageable);

    void saveWineryToDB(WineryInfo winery);

    WineryInfo getWineryById(Long id);

    void saveUserReviewToDB(WineryUserReview wineryUserReview);

    List<WineryUserReview> getUserReviewsById(Long wineryId);

    void updateNumRatings(Long wineryId);

    boolean shouldIncludeWinery(PlacesSearchResult result);

    String getWebsite(PlaceDetails detailedResult);

    String getPhoneNumber(PlaceDetails detailedResult);

    String getOpeningTime(PlaceDetails detailedResult);

    List<WineryReview> getReviews(PlaceDetails detailedResult);

}
