package com.tarun.movieReviewApp.services;
import com.tarun.movieReviewApp.payloads.ApiResponse;
import com.tarun.movieReviewApp.payloads.ReviewDto;

import java.util.List;


public interface ReviewService {
    ApiResponse<ReviewDto> getReviewById(Long reviewId);
    ApiResponse<List<ReviewDto>> getReviewsByMovieId(Long movieId);
    ApiResponse<ReviewDto> addReview(ReviewDto reviewDto, String username);
    ApiResponse<ReviewDto> updateReview(Long reviewId, ReviewDto reviewDetails);
    ApiResponse<Void> deleteReview(Long reviewId);
}