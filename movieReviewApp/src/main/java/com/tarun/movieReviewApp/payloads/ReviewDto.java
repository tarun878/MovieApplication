package com.tarun.movieReviewApp.payloads;

public class ReviewDto {
    private Long reviewId;
    private String comment;
    private int rating;
    private Long movieId;
    private String username;

    // Constructors
    public ReviewDto() {
    }

    public ReviewDto(Long reviewId, String comment, int rating, Long movieId, String username) {
        this.reviewId = reviewId;
        this.comment = comment;
        this.rating = rating;
        this.movieId = movieId;
        this.username = username;
    }

    // Getters and Setters
    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}