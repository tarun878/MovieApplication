package com.tarun.movieReviewApp.payloads;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter

public class MovieDto {
    private Long movieId;

    @NotBlank
    @Size(min = 3, message = "min size of movieTitle is 3 chars ")
    private String movieTitle;

    @NotBlank
    @Size(min = 3,message = "min size of movieDirector is 3 chars")
    private String movieDirector;
    @NotBlank
    @Size(min = 3,message = "min size of movieGenre is 3 chars")
    private String movieGenre;
    private Integer movieReleaseYear;
}


