package com.tarun.movieReviewApp.controller;
import com.tarun.movieReviewApp.payloads.ApiResponse;
import com.tarun.movieReviewApp.payloads.ReviewDto;
import com.tarun.movieReviewApp.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/{reviewId}")
    public ApiResponse<ReviewDto> getReviewById(@PathVariable Long reviewId) {
        return reviewService.getReviewById(reviewId);
    }

    @GetMapping("/movie/{movieId}")
    public ApiResponse<List<ReviewDto>> getReviewsByMovieId(@PathVariable Long movieId) {
        return reviewService.getReviewsByMovieId(movieId);
    }

    @PostMapping
    public ApiResponse<ReviewDto> addReview(@RequestBody ReviewDto reviewDto,
                                            @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        return reviewService.addReview(reviewDto, username);
    }

    @PutMapping("/{reviewId}")
    public ApiResponse<ReviewDto> updateReview(@PathVariable Long reviewId, @RequestBody ReviewDto reviewDto) {
        return reviewService.updateReview(reviewId, reviewDto);
    }

    @DeleteMapping("/{reviewId}")
    public ApiResponse<Void> deleteReview(@PathVariable Long reviewId) {
        return reviewService.deleteReview(reviewId);
    }
}