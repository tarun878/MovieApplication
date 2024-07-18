package com.tarun.movieReviewApp.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "movies")
@Getter
@NoArgsConstructor
@Setter
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieId;


    @Column(name = "title",nullable = false)
    private String movieTitle;
    @Column(name = "director",nullable = false)
    private String movieDirector;
    @Column(name = "genre",nullable = false)
    private String movieGenre;
    @Column(name = "releaseYear")
    private Integer movieReleaseYear;

    @OneToMany(mappedBy = "movie",cascade = CascadeType.ALL)
    private List<Review> reviews;
}

