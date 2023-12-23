package com.ukim.finki.domashna2.controller;

import com.ukim.finki.domashna2.model.WineryInfo;
import com.ukim.finki.domashna2.model.WineryUserReview;
import com.ukim.finki.domashna2.service.WineryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class DetailsController {

    @Autowired
    private WineryService wineryService;

    public DetailsController(WineryService wineryService) {
        this.wineryService = wineryService;
    }

    @GetMapping("Details/{id}")
    public String details(@PathVariable Long id, Model model) {
        WineryInfo winery = wineryService.getWineryById(id);
        model.addAttribute("winery", winery);
        List<WineryUserReview> wineryUserReviews = wineryService.getUserReviewsById(id);
        model.addAttribute("user_reviews", wineryUserReviews);
        return "Details";
    }

    @PostMapping("Details/{id}")
    public String addReview(@RequestParam("wineryId") Long wineryId,
                            @RequestParam("name") String name,
                            @RequestParam("rating") int rating,
                            @RequestParam("comment") String comment) {

        WineryUserReview wineryUserReview = new WineryUserReview();
        wineryUserReview.setWineryId(wineryId);
        wineryUserReview.setName(name);
        wineryUserReview.setRating(rating);
        wineryUserReview.setText(comment);

        wineryService.saveUserReviewToDB(wineryUserReview);

        return "redirect:/Details/"+wineryId;
    }

}



//  http://localhost:8080/Details/1

