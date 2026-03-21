package com.vinsguru.recommendation.mapper;

import com.vinsguru.netflux.events.CustomerGenreUpdatedEvent;
import com.vinsguru.netflux.events.MovieAddedEvent;
import com.vinsguru.recommendation.dto.MovieSummary;
import com.vinsguru.recommendation.entity.CustomerGenre;
import com.vinsguru.recommendation.entity.Movie;

public class RecommendationMapper {

    public static CustomerGenre toCustomerGenre(CustomerGenreUpdatedEvent genreUpdatedEvent){
        var entity = new CustomerGenre();
        entity.setCustomerId(genreUpdatedEvent.customerId());
        entity.setFavoriteGenre(genreUpdatedEvent.favoriteGenre());
        return entity;
    }

    public static Movie toMovie(MovieAddedEvent movieAddedEvent){
        var entity = new Movie();
        entity.setId(movieAddedEvent.movieId());
        entity.setTitle(movieAddedEvent.title());
        entity.setVoteCount(movieAddedEvent.voteCount());
        entity.setReleaseDate(movieAddedEvent.releaseDate());
        entity.setRuntime(movieAddedEvent.runtime());
        entity.setPosterPath(movieAddedEvent.posterPath());
        entity.setGenres(movieAddedEvent.genres());
        return entity;
    }

    public static MovieSummary toMovieSummary(Movie movie){
        return new MovieSummary(
                movie.getId(),
                movie.getTitle(),
                movie.getRuntime(),
                movie.getVoteCount(),
                movie.getReleaseDate(),
                movie.getGenres(),
                movie.getPosterPath()
        );
    }

}
