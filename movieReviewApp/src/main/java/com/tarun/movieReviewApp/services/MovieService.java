package com.tarun.movieReviewApp.services;

import com.tarun.movieReviewApp.payloads.MovieDto;

import java.util.List;

public interface MovieService {
    //create
    MovieDto createMovie(MovieDto movieDto);
    //update
    MovieDto updateMovie(MovieDto movieDto,Long movieId);
    //delete
    void deleteMovie(Long movieId);
    //get
    MovieDto getMovie(Long movieId);
    //get all
    List<MovieDto> getMovies();


}
