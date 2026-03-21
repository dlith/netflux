package com.vinsguru.customer.messaging;

import com.vinsguru.netflux.events.CustomerGenreUpdatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class CustomerEventPublisher {

    private static final Logger log = LoggerFactory.getLogger(CustomerEventPublisher.class);
    private final StreamBridge streamBridge;
    private static final String CUSTOMER_EVENTS_OUTS = "customer-events-out";

    public CustomerEventPublisher(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @EventListener
    public void onGenreUpdatedEvent(CustomerGenreUpdatedEvent genreUpdatedEvent) {
        Message<CustomerGenreUpdatedEvent> message = MessageBuilder.withPayload(genreUpdatedEvent)
                .setHeader(KafkaHeaders.KEY, genreUpdatedEvent.customerId())
                .build();
        this.streamBridge.send(CUSTOMER_EVENTS_OUTS, message);
        log.info("published: {}", message);
    }

}
