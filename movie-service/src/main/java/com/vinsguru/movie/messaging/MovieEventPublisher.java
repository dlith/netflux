package com.vinsguru.movie.messaging;

import com.vinsguru.netflux.events.MovieAddedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class MovieEventPublisher {

    private static final Logger logger = LoggerFactory.getLogger(MovieEventPublisher.class);
    private final StreamBridge streamBridge;
    private static final String MOVIE_EVENTS_OUT = "movie-events-out";

    public MovieEventPublisher(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @EventListener
    public void onMovieAdded(MovieAddedEvent movieAddedEvent) {
        var message= MessageBuilder.withPayload(movieAddedEvent)
                .setHeader(KafkaHeaders.KEY, movieAddedEvent.movieId().toString())
                .build();
        this.streamBridge.send(MOVIE_EVENTS_OUT, message);
        logger.info("Publishing MovieAddedEvent for movieId: {}", movieAddedEvent.movieId());
    }

}
