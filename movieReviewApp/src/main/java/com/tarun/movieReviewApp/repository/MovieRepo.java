package com.tarun.movieReviewApp.repository;

import com.tarun.movieReviewApp.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MovieRepo extends JpaRepository<Movie,Long> {
}
