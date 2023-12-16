package com.ukim.finki.domashna2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class WineryUserReview {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long Id;
    Long wineryId;
    String name;
    int rating;
    String text;
    String language;

}
