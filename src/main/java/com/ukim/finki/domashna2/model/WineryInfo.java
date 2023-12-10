package com.ukim.finki.domashna2.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
//@Table(name = "winery", schema = "winery")
public class WineryInfo {
    @Column(unique = true)
    public String name;
    public String address;
    public String location;
    public float rating;
    public int numRatings;
    public String phoneNum;
    public String placeId;
    @Column(length = 1000)
    public String openingHours;
    @Column(length = 1000)
    public String webSite;
    @ElementCollection
    @Column(length = 2000)
    public List<WineryReview> reviews;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public WineryInfo(String name, String address, String location, float rating, int numRatings, String phoneNum, String placeId,String openingHours,String webSite,List<WineryReview> reviews) {
        this.name = name;
        this.address = address;
        this.location = location;
        this.rating = rating;
        this.numRatings=numRatings;
        this.phoneNum=phoneNum;
        this.placeId=placeId;
        this.openingHours = openingHours;
        this.webSite=webSite;
        this.reviews=reviews;
    }

    public WineryInfo() {

    }
}