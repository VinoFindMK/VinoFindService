package com.ukim.finki.domashna2.service;

import com.ukim.finki.domashna2.model.WineryInfo;
import com.ukim.finki.domashna2.model.WineryUserReview;
import com.ukim.finki.domashna2.repository.WineryRepository;
import com.ukim.finki.domashna2.repository.WineryUserReviewRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class WineryService {

    @Autowired
    private WineryRepository wineryRepository;

    @Autowired
    private WineryUserReviewRepository wineryUserReviewRepository;

    public Page<WineryInfo> getAllWineries(Pageable pageable) {
        return wineryRepository.findAll(pageable);
    }


    public Page<WineryInfo> searchWineries(String query, Pageable pageable) {
        return wineryRepository.findByNameContainingIgnoreCase(query, pageable);
    }


    public void saveWineryToDB(WineryInfo winery) {

        Optional<WineryInfo> existingWinery = wineryRepository.findByName(winery.getName());
        if (!existingWinery.isPresent()) {
            wineryRepository.save(winery);
           // System.out.println(winery);
        }
    }

    public WineryInfo getWineryById(Long id) {
        return wineryRepository.findById(id).orElse(null);
    }

    public void saveUserReviewToDB(WineryUserReview wineryUserReview){
        wineryUserReviewRepository.save(wineryUserReview);
    }

    public List<WineryUserReview> getUserReviewsById(Long wineryId){
        return wineryUserReviewRepository.getAllByWineryIdEquals(wineryId);
    }

    @Transactional
    public void updateNumRatings(Long wineryId) {
        WineryInfo wineryInfo = wineryRepository.findById(wineryId).orElse(null);
        if (wineryInfo != null) {
            int totalGMRatings = wineryInfo.getReviews().size();
            int totalUserRatings = wineryUserReviewRepository.getAllByWineryIdEquals(wineryId).size();
            System.out.println(totalGMRatings);
            System.out.println(totalUserRatings);
            List<Float> UserRatings= wineryUserReviewRepository.getAllRatingsByWineryId(wineryId);
            List<Float> GMRatings= wineryRepository.getRatingsByWineryId(wineryId);
            List<Float> AllRatings = new ArrayList<>(UserRatings);
            AllRatings.addAll(GMRatings);
            double averageRating = AllRatings.stream()
                    .mapToDouble(Float::doubleValue)
                    .average()
                    .orElse(0.0);
            float roundedAverageRating = (float) Math.round(averageRating * 10) / 10;
            wineryInfo.setNumRatings(totalGMRatings+totalUserRatings);
            wineryInfo.setRating((float) roundedAverageRating);
            wineryRepository.save(wineryInfo);
        }
    }

}
