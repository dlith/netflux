package com.vinsguru.recommendation.service;

import com.vinsguru.recommendation.dto.MovieSummary;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class RecommendationService {

    public List<MovieSummary> findNewlyAdded() {
        return Collections.emptyList();
    }

    public List<MovieSummary> findPersonalized(Integer customerId) {
        return Collections.emptyList();
    }

    public MovieSummary findMovie(Integer movieId) {
        return null;
    }

}
