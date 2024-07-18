package com.tarun.movieReviewApp.repository;

import com.tarun.movieReviewApp.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepo extends JpaRepository<Review,Long> {
}
