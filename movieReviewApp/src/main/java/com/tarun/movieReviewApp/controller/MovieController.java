package com.tarun.movieReviewApp.controller;

import com.tarun.movieReviewApp.payloads.ApiResponse;
import com.tarun.movieReviewApp.payloads.MovieDto;
import com.tarun.movieReviewApp.services.MovieService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    @Autowired
    private MovieService movieService;
    @Autowired
    private ModelMapper modelMapper;

    //create movie
    @PostMapping("/")
    public ResponseEntity<MovieDto> createMovie(@Valid @RequestBody MovieDto movieDto){
        MovieDto createMovie = this.movieService.createMovie(movieDto);
        return new ResponseEntity<MovieDto>(createMovie, HttpStatus.CREATED);
    }

    //update movie
    @PutMapping("/{movId}")
    public ResponseEntity<MovieDto> updateMovie(@Valid @RequestBody MovieDto movieDto, @PathVariable Long movId) {
        MovieDto updatedMovie = this.movieService.updateMovie(movieDto, movId);
        return new ResponseEntity<MovieDto>(updatedMovie, HttpStatus.OK);
    }
    //delete movie
    @DeleteMapping("/{movId}")
    public ResponseEntity<ApiResponse> deleteMovie(@PathVariable Long movId) {
        this.movieService.deleteMovie(movId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Movie deleted successfully", true), HttpStatus.OK);
    }
    //get movie
    @GetMapping("/{movId}")
    public ResponseEntity<MovieDto> getMovie(@PathVariable Long movId){
        MovieDto movieDto = this.movieService.getMovie(movId);
        return new ResponseEntity<MovieDto>(movieDto,HttpStatus.OK);
    }
    //get all movies
    @GetMapping("/")
    public ResponseEntity<List<MovieDto>> getMovies(){
        List<MovieDto> movies = this.movieService.getMovies();
        return  ResponseEntity.ok(movies);
    }
}


