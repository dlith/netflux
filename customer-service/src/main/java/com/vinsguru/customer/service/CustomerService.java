package com.vinsguru.customer.service;

import com.vinsguru.customer.dto.CustomerDetails;
import com.vinsguru.customer.dto.GenreUpdateRequest;
import com.vinsguru.customer.entity.Customer;
import com.vinsguru.customer.exception.CustomerNotFoundException;
import com.vinsguru.customer.mapper.CustomerMapper;
import com.vinsguru.customer.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public CustomerService(CustomerRepository customerRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.customerRepository = customerRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public CustomerDetails getCustomer(Integer customerId) {
        return this.customerRepository.findById(customerId).map(CustomerMapper::toCustomerDetails)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
    }

    @Transactional
    public void updateCustomerGenre(Integer customerId, GenreUpdateRequest request) {
        Customer customer = this.customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
        customer.setFavoriteGenre(request.favoriteGenre());
        this.applicationEventPublisher.publishEvent(CustomerMapper.toGenreUpdatedEvent(customerId, request.favoriteGenre()));
    }

}
