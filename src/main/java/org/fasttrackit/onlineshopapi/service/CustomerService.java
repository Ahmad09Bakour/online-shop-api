package org.fasttrackit.onlineshopapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fasttrackit.onlineshopapi.domain.Customer;
import org.fasttrackit.onlineshopapi.domain.exception.ResourceNotFoundException;
import org.fasttrackit.onlineshopapi.persistence.CustomerRepository;
import org.fasttrackit.onlineshopapi.transfer.customer.CreateCustomerRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository customerRepository; // first step, get logic from Repos. we make it final because we don't
    // want to get anything back, data enters only once , which will require us to make a constructor out of it as shown below.

    private final ObjectMapper objectMapper;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, ObjectMapper objectMapper) {
        this.customerRepository = customerRepository;
        this.objectMapper = objectMapper;
    }

    public Customer createCustomer(CreateCustomerRequest request){
        LOGGER.info("Creating Customer {}", request);
        // I can type Customer customer = new Customer; >> request.setId = customer.getId()... setName..etc
        // but I don't want to do that, I want to put all the values in a container and let that container take responsibility
        // of all that long logic, and for that I call ObjectMapper and store all that long code in it and ask it to translate
        // from the type of class Customer to the request (which will be like query..).
        Customer customer = objectMapper.convertValue(request, Customer.class);
        return customerRepository.save(customer);   // then I save it in the repository
    }

    public Customer getCustomer (long id) throws ResourceNotFoundException {
        LOGGER.info("Retrieving customer {}", id);

        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer " + id + " doesn't exist."));

    }
}
