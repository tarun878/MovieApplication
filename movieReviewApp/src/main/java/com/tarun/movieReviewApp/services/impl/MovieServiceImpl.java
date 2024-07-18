package com.tarun.movieReviewApp.services.impl;

import com.tarun.movieReviewApp.entities.Movie;
import com.tarun.movieReviewApp.exception.ResourceNotFoundException;
import com.tarun.movieReviewApp.payloads.MovieDto;
import com.tarun.movieReviewApp.repository.MovieRepo;
import com.tarun.movieReviewApp.services.MovieService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    private MovieRepo movieRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public MovieDto createMovie(MovieDto movieDto) {
        Movie mov = this.modelMapper.map(movieDto, Movie.class);
        Movie addedMovie = this.movieRepo.save(mov);
        return this.modelMapper.map(addedMovie,MovieDto.class);
    }

    @Override
    public MovieDto updateMovie(MovieDto movieDto, Long movieId) {
        Movie mov = this.movieRepo.findById(movieId)
                .orElseThrow(()-> new ResourceNotFoundException("Movie","Movie Id",movieId));
        mov.setMovieTitle(movieDto.getMovieTitle());
        mov.setMovieDirector(movieDto.getMovieDirector());
        mov.setMovieGenre(movieDto.getMovieGenre());
        mov.setMovieReleaseYear(movieDto.getMovieReleaseYear());
        Movie updatedMov = this.movieRepo.save(mov);
        return this.modelMapper.map(updatedMov,MovieDto.class);
    }

    @Override
    public void deleteMovie(Long movieId) {
        Movie mov = this.movieRepo.findById(movieId)
                .orElseThrow(()->new ResourceNotFoundException("Movie","Movie Id",movieId));
        this.movieRepo.delete(mov);

    }

    @Override
    public MovieDto getMovie(Long movieId) {
        Movie mov = this.movieRepo.findById(movieId)
                .orElseThrow(()->new ResourceNotFoundException("Movie","Movie Id",movieId));
        return this.modelMapper.map(mov,MovieDto.class);
    }

    @Override
    public List<MovieDto> getMovies() {

        List<Movie> Movies = this.movieRepo.findAll();
        List<MovieDto> movieDtos = Movies.stream().map((mov)->this.modelMapper.map(mov, MovieDto.class)).collect(Collectors.toList());
        return movieDtos;
    }
}
