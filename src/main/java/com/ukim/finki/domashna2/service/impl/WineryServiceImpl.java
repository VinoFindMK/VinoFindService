package com.ukim.finki.domashna2.service.impl;

import com.ukim.finki.domashna2.model.WineryUserReview;
import com.ukim.finki.domashna2.repository.WineryUserReviewRepository;
import com.ukim.finki.domashna2.service.WineryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class WineryServiceImpl implements WineryService {

    @Autowired
    private WineryUserReviewRepository wineryUserReviewRepository;
    @Override
    public void saveUserReviewToDB(WineryUserReview wineryUserReview) {
        wineryUserReviewRepository.save(wineryUserReview);
    }
}
