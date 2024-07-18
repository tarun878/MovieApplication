package com.tarun.movieReviewApp.services.impl;


import com.tarun.movieReviewApp.entities.Movie;
import com.tarun.movieReviewApp.entities.Review;
import com.tarun.movieReviewApp.exception.ResourceNotFoundException;
import com.tarun.movieReviewApp.payloads.MovieDto;
import com.tarun.movieReviewApp.payloads.ReviewDto;
import com.tarun.movieReviewApp.repository.ReviewRepo;
import com.tarun.movieReviewApp.services.ReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewRepo reviewRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ReviewDto createReview(ReviewDto reviewDto) {
        Review rev = this.modelMapper.map(reviewDto, Review.class);
        Review addedReview = this.reviewRepo.save(rev);
        return this.modelMapper.map(addedReview, ReviewDto.class);
    }

    @Override
    public ReviewDto updateReview(ReviewDto reviewDto, Long reviewId) {

        Review rev = this.reviewRepo.findById(reviewId)
                .orElseThrow(()-> new ResourceNotFoundException("Review","Review Id",reviewId));
        rev.setComment(reviewDto.getComment());
        rev.setRating(reviewDto.getRating());
        Review updatedRev = this.reviewRepo.save(rev);
        return this.modelMapper.map(updatedRev,ReviewDto.class);
    }

    @Override
    public void deleteReview(Long reviewId) {
        Review rev = this.reviewRepo.findById(reviewId)
                .orElseThrow(()->new ResourceNotFoundException("Review","Review Id",reviewId));
        this.reviewRepo.delete(rev);

    }

    @Override
    public ReviewDto getReview(Long reviewId) {
        Review rev = this.reviewRepo.findById(reviewId)
                .orElseThrow(()->new ResourceNotFoundException("Review","Review Id",reviewId));
        return this.modelMapper.map(rev,ReviewDto.class);
    }

    @Override
    public List<ReviewDto> getReviews() {
        List<Review> reviews = this.reviewRepo.findAll();
        List<ReviewDto> reviewDtos = reviews.stream().map((rev)->this.modelMapper.map(rev, ReviewDto.class)).collect(Collectors.toList());
        return reviewDtos;
    }
}
