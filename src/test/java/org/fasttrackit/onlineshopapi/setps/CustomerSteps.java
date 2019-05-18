package org.fasttrackit.onlineshopapi.setps;

import org.fasttrackit.onlineshopapi.domain.Customer;
import org.fasttrackit.onlineshopapi.service.CustomerService;
import org.fasttrackit.onlineshopapi.transfer.customer.CreateCustomerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

@Component
public class CustomerSteps {

    @Autowired
    private CustomerService customerService;

    public Customer createCustomerMethod() {
        CreateCustomerRequest request = new CreateCustomerRequest();
        request.setName("John");


        Customer customer = customerService.createCustomer(request);

        assertThat("No customer returned", customer, notNullValue()); // give me an error with this massage that we want to get once the object "customer" when the following condition doesn't occur
        assertThat("Customer does not have an auto-generated id", customer.getId(), greaterThan(0L));
        assertThat(customer.getName(), is(request.getName())); // no massage, but give me an error once the name of the customer doesn't match with the request


        return customer;
    }
}
