package com.tarun.movieReviewApp.services.impl;

import com.tarun.movieReviewApp.entities.Review;
import com.tarun.movieReviewApp.entities.User;
import com.tarun.movieReviewApp.exception.ResourceNotFoundException;
import com.tarun.movieReviewApp.payloads.ApiResponse;
import com.tarun.movieReviewApp.payloads.ReviewDto;
import com.tarun.movieReviewApp.repository.ReviewRepo;
import com.tarun.movieReviewApp.repository.UserRepo;
import com.tarun.movieReviewApp.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepo reviewRepo;
    private final UserRepo userRepo;

    @Autowired
    public ReviewServiceImpl(ReviewRepo reviewRepo, UserRepo userRepo) {
        this.reviewRepo = reviewRepo;
        this.userRepo = userRepo;
    }

    @Override
    public ApiResponse<ReviewDto> getReviewById(Long reviewId) {
        Review review = reviewRepo.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + reviewId));
        return new ApiResponse<>(true, "Review retrieved successfully", convertToDto(review));
    }

    @Override
    public ApiResponse<List<ReviewDto>> getReviewsByMovieId(Long movieId) {
        List<Review> reviews = reviewRepo.findByMovieId(movieId);
        if (reviews.isEmpty()) {
            throw new ResourceNotFoundException("No reviews found for movie with id: " + movieId);
        }
        List<ReviewDto> reviewDTOs = reviews.stream().map(this::convertToDto).collect(Collectors.toList());
        return new ApiResponse<>(true, "Reviews retrieved successfully", reviewDTOs);
    }

    @Override
    public ApiResponse<ReviewDto> addReview(ReviewDto reviewDto, String username) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));

        Review review = new Review();
        review.setComment(reviewDto.getComment());
        review.setRating(reviewDto.getRating());
        review.setMovieId(reviewDto.getMovieId());
        review.setUser(user);

        Review savedReview = reviewRepo.save(review);
        return new ApiResponse<>(true, "Review added successfully", convertToDto(savedReview));
    }

    @Override
    public ApiResponse<ReviewDto> updateReview(Long reviewId, ReviewDto reviewDetails) {
        Review review = reviewRepo.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + reviewId));

        review.setComment(reviewDetails.getComment());
        review.setRating(reviewDetails.getRating());
        review.setMovieId(reviewDetails.getMovieId());

        Review updatedReview = reviewRepo.save(review);
        return new ApiResponse<>(true, "Review updated successfully", convertToDto(updatedReview));
    }

    @Override
    public ApiResponse<Void> deleteReview(Long reviewId) {
        Review review = reviewRepo.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + reviewId));

        reviewRepo.delete(review);
        return new ApiResponse<>(true, "Review deleted successfully", null);
    }

    private ReviewDto convertToDto(Review review) {
        return new ReviewDto(
                review.getReviewId(),
                review.getComment(),
                review.getRating(),
                review.getMovieId(),
                review.getUser().getUsername()
        );
    }
}
