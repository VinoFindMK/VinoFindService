package com.ukim.finki.domashna2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.time.Instant;

@Embeddable
public class WineryReview {
    public String authorName;
    public String language;
    public String profilePhotoUrl;
    public String relativeTimeDescription;
    @Column(length = 2000)
    public String text;
    public long time;
    public int rating;

    public WineryReview(String authorName, String language, String profilePhotoUrl, String relativeTimeDescription, String text, Instant time, int rating) {
        this.authorName = authorName;
        this.language = language;
        this.profilePhotoUrl = profilePhotoUrl;
        this.relativeTimeDescription = relativeTimeDescription;
        this.text = text;
        this.time = time.toEpochMilli();
        this.rating = rating;
    }

    public WineryReview() {

    }
}
