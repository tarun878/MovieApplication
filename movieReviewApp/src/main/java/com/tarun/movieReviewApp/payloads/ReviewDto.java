package com.tarun.movieReviewApp.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReviewDto {
    private Long reviewId;
    @NotBlank
    @Size(min = 3,message = "min size is 3 chars")
    private String comment;
    private int rating;
}
