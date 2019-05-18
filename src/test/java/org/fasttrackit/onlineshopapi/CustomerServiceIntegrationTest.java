package org.fasttrackit.onlineshopapi;

import org.fasttrackit.onlineshopapi.service.CustomerService;
import org.fasttrackit.onlineshopapi.setps.CustomerSteps;
import org.fasttrackit.onlineshopapi.transfer.customer.CreateCustomerRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest

public class CustomerServiceIntegrationTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerSteps customerSteps;

    @Test
    public void testCreateCustomer_whenValidRequest_thenReturnStoredCustomer(){

        customerSteps.createCustomerMethod();

    }

    @Test(expected = ConstraintViolationException.class)
    public void testCreateCustomer_whenMissingMandatoryDetails_thenThrowConstraintViolationException(){
          // we want to test creating a customer_when one or all details are missing (name, id..)_then throw the following exception
        customerService.createCustomer(new CreateCustomerRequest());
    }

//    @Test
//    public void testUpdateCustomer_whenValidRequest_thenReturnUpdatedCustomer() throws ResourceNotFoundException {
//        Customer customer = createCustomerMethod();
//
//        UpdateCustomerRequest request = new UpdateCustomerRequest();
//        request.setName(customer.getName() + "updated");
//        request.setQuantity(customer.getQuantity() + 10);
//        request.setPrice(customer.getPrice() + 10);
//
//        Customer updatedProducct = customerService.updateCustomer(customer.getId(), request);
//
//        assertThat(updatedProducct, notNullValue());
//        assertThat(updatedProducct.getId(), is(customer.getId()));
//        assertThat(updatedProducct.getName(), is(request.getName()));
//        assertThat(updatedProducct.getQuantity(), is(request.getQuantity()));
//        assertThat(updatedProducct.getPrice(), is(request.getPrice()));
//


    // todo: add more tests more scernarios
}
