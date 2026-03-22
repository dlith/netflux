package com.vinsguru.movie.service;


import com.vinsguru.movie.dto.MovieDetails;
import com.vinsguru.movie.entity.Movie;
import com.vinsguru.movie.exception.MovieNotFoundException;
import com.vinsguru.movie.mapper.MovieMapper;
import com.vinsguru.movie.repository.MovieRepository;
import jakarta.transaction.Transactional;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final ApplicationEventPublisher eventPublisher;

    public MovieService(MovieRepository movieRepository, ApplicationEventPublisher eventPublisher) {
        this.movieRepository = movieRepository;
        this.eventPublisher = eventPublisher;
    }

    public MovieDetails getMovie(Integer movieId) {
        return this.movieRepository.findById(movieId)
                .map(MovieMapper::toMovieDetails)
                .orElseThrow(() -> new MovieNotFoundException(movieId));
    }


    @Transactional
    public MovieDetails saveMovie(MovieDetails movieDetails) {
        Movie movie = this.movieRepository.save(MovieMapper.toMovie(movieDetails));
        this.eventPublisher.publishEvent(MovieMapper.toMovieAddedEvent(movie));
        return MovieMapper.toMovieDetails(movie);
    }

}
