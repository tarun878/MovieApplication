package com.tarun.movieReviewApp.repository;

import com.tarun.movieReviewApp.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepo extends JpaRepository<Review, Long> {
   List<Review> findByMovieId(Long movieId);
}