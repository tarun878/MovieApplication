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
    @PostMapping("/")
    public ResponseEntity<ReviewDto> createReview(@Valid @RequestBody ReviewDto reviewDto){
        ReviewDto createReview = this.reviewService.createReview(reviewDto);
        return new ResponseEntity<ReviewDto>(createReview, HttpStatus.CREATED);
    }

    //update review
    @PutMapping("/{revId}")
    public ResponseEntity<ReviewDto> updateReview(@Valid @RequestBody ReviewDto reviewDto, @PathVariable Long revId) {
        ReviewDto updatedReview = this.reviewService.updateReview(reviewDto, revId);
        return new ResponseEntity<ReviewDto>(updatedReview, HttpStatus.OK);
    }
    //delete review
    @DeleteMapping("/{revId}")
    public ResponseEntity<ApiResponse> deleteReview(@PathVariable Long revId) {
        this.reviewService.deleteReview(revId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Review deleted successfully", true), HttpStatus.OK);
    }
    //get review
    @GetMapping("/{revId}")
    public ResponseEntity<ReviewDto> getReview(@PathVariable Long revId){
        ReviewDto reviewDto = this.reviewService.getReview(revId);
        return new ResponseEntity<ReviewDto>(reviewDto,HttpStatus.OK);
    }
    //get all reviews
    @GetMapping("/")
    public ResponseEntity<List<ReviewDto>> getReviews(){
        List<ReviewDto> reviews = this.reviewService.getReviews();
        return  ResponseEntity.ok(reviews);
    }
}
