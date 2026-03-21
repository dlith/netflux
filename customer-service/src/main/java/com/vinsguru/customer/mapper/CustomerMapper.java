package com.vinsguru.customer.mapper;

import com.vinsguru.customer.dto.CustomerDetails;
import com.vinsguru.customer.entity.Customer;
import com.vinsguru.netflux.events.CustomerGenreUpdatedEvent;

import java.time.Instant;

public class CustomerMapper {

    public static CustomerDetails toCustomerDetails(Customer customer){
        return new CustomerDetails(
                customer.getId(),
                customer.getName(),
                customer.getFavoriteGenre()
        );
    }

    public static CustomerGenreUpdatedEvent toGenreUpdatedEvent(Integer customerId, String favoriteGenre) {
        return new CustomerGenreUpdatedEvent(
                customerId,
                favoriteGenre,
                Instant.now()
        );
    }

}
