package com.ukim.finki.domashna2.service;

import com.google.maps.model.PlaceDetails;
import com.ukim.finki.domashna2.model.WineryReview;

import java.util.ArrayList;
import java.util.List;

public class Utility {
    public static String getWebsite(PlaceDetails detailedResult) {
        if(detailedResult.website == null){
            return "Unknown";
        }
        return String.valueOf(detailedResult.website);
    }
    public static String getOpeningTime(PlaceDetails detailedResult) {
        if (detailedResult.openingHours != null && detailedResult.openingHours.periods != null
                && detailedResult.openingHours.periods.length > 1) {
            StringBuilder stringBuilder = new StringBuilder();
            String result = "";
            for(int i=0;i<detailedResult.openingHours.periods.length;i++){
                stringBuilder.append(String.valueOf(detailedResult.openingHours.periods[i].open.day));
                stringBuilder.append(" ");
                stringBuilder.append(String.valueOf(detailedResult.openingHours.periods[i].open.time));
                stringBuilder.append(" - ");
                stringBuilder.append(String.valueOf(detailedResult.openingHours.periods[i].close.day));
                stringBuilder.append(" ");
                stringBuilder.append(String.valueOf(detailedResult.openingHours.periods[i].close.time));
                stringBuilder.append("                                   ");
                result = stringBuilder.toString();
            }
            return result;
        } else  if (detailedResult.openingHours != null && detailedResult.openingHours.openNow) {
            return "Open 24/7";
        }else{
            return "Unknown";
        }
    }

    public static List<WineryReview> getReviews(PlaceDetails detailedResult) {
        List<WineryReview> reviews = new ArrayList<>();

        if (detailedResult.reviews != null) {
            for (com.google.maps.model.PlaceDetails.Review googleReview : detailedResult.reviews) {
                WineryReview review = new WineryReview(
                        googleReview.authorName,
                        googleReview.language,
                        googleReview.profilePhotoUrl,
                        googleReview.relativeTimeDescription,
                        googleReview.text,
                        googleReview.time,
                        googleReview.rating
                );
                reviews.add(review);
            }
        }

        return reviews;
    }
}
