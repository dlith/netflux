package com.vinsguru.recommendation.controller;

import com.vinsguru.recommendation.dto.MovieRecommendations;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    @GetMapping("/{customerId}")
    public List<MovieRecommendations> getRecommendations(@PathVariable Integer customerId){
        return Collections.emptyList();
    }

    @GetMapping(value = "/{customerId}/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<MovieRecommendations> getRecommendationStream(@PathVariable Integer customerId){
        return Flux.empty();
    }

}
