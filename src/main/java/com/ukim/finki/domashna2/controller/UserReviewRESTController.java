package com.ukim.finki.domashna2.controller;


import com.ukim.finki.domashna2.model.WineryInfo;
import com.ukim.finki.domashna2.model.WineryUserReview;
import com.ukim.finki.domashna2.repository.WineryRepository;
import com.ukim.finki.domashna2.service.impl.WineryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserReviewRESTController {
    private final WineryRepository wineryRepository;
    private final WineryServiceImpl service;
    public UserReviewRESTController(WineryRepository wineryRepository, WineryServiceImpl service) {
        this.wineryRepository = wineryRepository;
        this.service = service;
    }
    @GetMapping("/rest")
    public List<WineryInfo> getAllWineries() {
        return wineryRepository.findAll();
    }
    @GetMapping("/userreviews/{id}")
    public ResponseEntity<List<WineryUserReview>> getResponse(@PathVariable String id) {
        System.out.println(service.getUserReviewsById(Long.valueOf(id)));
        if (service.getUserReviewsById(Long.valueOf(id)) == null) {
            System.out.println(service.getUserReviewsById(Long.valueOf(id)));
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
       return new ResponseEntity<>(service.getUserReviewsById(Long.valueOf(id)), HttpStatus.OK);
    }
}
//       http://localhost:8080/userreviews/1
