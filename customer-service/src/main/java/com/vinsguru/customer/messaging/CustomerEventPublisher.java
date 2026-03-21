package com.vinsguru.customer.messaging;

import com.vinsguru.netflux.events.CustomerGenreUpdatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class CustomerEventPublisher {

    @EventListener
    public void onGenreUpdatedEvent(CustomerGenreUpdatedEvent genreUpdatedEvent) {

    }

}
