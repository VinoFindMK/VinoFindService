package com.ukim.finki.domashna2.service;

import com.ukim.finki.domashna2.model.WineryInfo;
import com.ukim.finki.domashna2.model.WineryReview;
import com.ukim.finki.domashna2.model.WineryUserReview;
import com.ukim.finki.domashna2.repository.WineryRepository;
import com.ukim.finki.domashna2.repository.WineryUserReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    public List<WineryUserReview> getUserReviewsByLanguage(Long wineryId, String language){
        return wineryUserReviewRepository.getAllByWineryIdEqualsAndLanguageEquals(wineryId, language);
    }
}
