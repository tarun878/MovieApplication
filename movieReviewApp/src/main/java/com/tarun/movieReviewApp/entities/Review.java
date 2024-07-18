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
    private Long reviewId;
    @Column(name = "comment",nullable = false)
    private String comment;
    @Column(name = "rating")
    private int rating;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
