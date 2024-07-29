package com.tarun.movieReviewApp.controller;
import com.tarun.movieReviewApp.payloads.ApiResponse;
import com.tarun.movieReviewApp.payloads.ReviewDto;
import com.tarun.movieReviewApp.services.ReviewService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/reviews")

public class ReviewController {
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private ModelMapper modelMapper;

    //create Review
    @PostMapping
    public ResponseEntity<ReviewDto> createReview(@Valid @RequestBody ReviewDto reviewDto) {
        ReviewDto createReview = this.reviewService.createReview(reviewDto);
        return new ResponseEntity<ReviewDto>(createReview, HttpStatus.CREATED);
    }

    //update review
    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewDto> updateReview(@Valid @RequestBody ReviewDto reviewDto, @PathVariable Long reviewId) {
        ReviewDto updatedReview = this.reviewService.updateReview(reviewDto, reviewId);
        return new ResponseEntity<ReviewDto>(updatedReview, HttpStatus.OK);
    }

    //delete review
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ApiResponse> deleteReview(@PathVariable Long reviewId) {
        this.reviewService.deleteReview(reviewId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Review deleted successfully", true), HttpStatus.OK);
    }

    //get review
    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewDto> getReview(@PathVariable Long reviewId) {
        ReviewDto reviewDto = this.reviewService.getReview(reviewId);
        return new ResponseEntity<ReviewDto>(reviewDto, HttpStatus.OK);
    }
//
    //get all reviews
    @GetMapping
    public ResponseEntity<List<ReviewDto>> getReviews() {
        List<ReviewDto> reviews = this.reviewService.getReviews();
        return ResponseEntity.ok(reviews);
    }
    //get all reviews for a specific movie by movieId

    @GetMapping("/movieId")
    public List<ReviewDto> getReviewsByMovieId(@RequestBody Long movieId) {
        // Call your service to retrieve reviews by movie ID
        return reviewService.getReviewsByMovieId(movieId);
    }
}
