package com.tarun.movieReviewApp.services;

import com.tarun.movieReviewApp.payloads.ReviewDto;

import java.util.List;

public interface ReviewService {
    //create
    ReviewDto createReview(ReviewDto reviewDto);
    //update
    ReviewDto updateReview(ReviewDto reviewDto,Long reviewId);
    //delete
    void deleteReview(Long reviewId);
    //get
    ReviewDto getReview(Long reviewId);
    //get all
    List<ReviewDto> getReviews();


}
