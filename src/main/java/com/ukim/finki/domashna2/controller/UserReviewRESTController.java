package com.ukim.finki.domashna2.controller;

import com.ukim.finki.domashna2.model.WineryInfo;
import com.ukim.finki.domashna2.model.WineryUserReview;
import com.ukim.finki.domashna2.repository.WineryRepository;
import com.ukim.finki.domashna2.service.impl.WineryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserReviewRESTController {

    private final WineryRepository wineryRepository;
    private final WineryServiceImpl service;

    @Autowired
    public UserReviewRESTController(WineryRepository wineryRepository, WineryServiceImpl service) {
        this.wineryRepository = wineryRepository;
        this.service = service;
    }

    @PostMapping("/wineries/{wineryId}/reviews")
    public ResponseEntity<WineryUserReview> addReviewToWinery(
            @PathVariable Long wineryId,
            @RequestBody WineryUserReview newReview) {

        WineryInfo wineryInfo = wineryRepository.findById(wineryId).orElse(null);
        if (wineryInfo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        newReview.setWineryId(wineryId);
        service.saveUserReviewToDB(newReview);

        return new ResponseEntity<>(newReview, HttpStatus.CREATED);
    }
}
