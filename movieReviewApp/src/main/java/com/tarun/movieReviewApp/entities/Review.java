package com.tarun.movieReviewApp.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reviews")
@NoArgsConstructor
@Getter
@Setter

public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reviewId")
    private Long reviewId;
    private Long movieId;
    @Column(name = "reviewer",nullable = false)
    private String reviewerName;
    @Column(name = "comment",nullable = false)
    private String comment;
    @Column(name = "rating")
    private int rating;


}
