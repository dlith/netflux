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

    @EventListener
    public void onMovieAdded(MovieAddedEvent movieAddedEvent) {
    }

}
