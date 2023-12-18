package com.ukim.finki.domashna2.controller;

import com.ukim.finki.domashna2.model.WineryUserReview;
import com.ukim.finki.domashna2.service.WineryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reviews")
public class UserReviewController {

    @Autowired
    private final WineryService wineryService;

    public UserReviewController(WineryService wineryService) {
        this.wineryService = wineryService;
    }

    //This method is simply for testing rn...
    @GetMapping("/{wineryId}")
    public String getUserReviewsForWinery(@PathVariable("wineryId") Long wineryId){
        System.out.println(wineryService.getUserReviewsById(wineryId).toString());
        return "redirect:/Details/"+wineryId;
    }

    @GetMapping("/add/{wineryId}")
    public String showReviewForm(@PathVariable Long wineryId, Model model) {
        model.addAttribute("wineryId", wineryId);
        return "comments";
    }

    @PostMapping("/add")
    public String addReview(@RequestParam("wineryid") Long wineryId,
                            @RequestParam("name") String name,
                            @RequestParam("surname") String surname,
                            @RequestParam("rating") int rating,
                            @RequestParam("comment") String comment) {

        WineryUserReview wineryUserReview = new WineryUserReview();
        wineryUserReview.setWineryId(wineryId);
        wineryUserReview.setName(name + " " + surname);
        wineryUserReview.setRating(rating);
        wineryUserReview.setText(comment);

        wineryService.saveUserReviewToDB(wineryUserReview);

        return "redirect:/Details/"+wineryId;
    }
}
