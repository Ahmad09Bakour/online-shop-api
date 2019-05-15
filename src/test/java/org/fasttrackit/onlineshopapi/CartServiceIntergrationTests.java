package org.fasttrackit.onlineshopapi;

import org.fasttrackit.onlineshopapi.domain.Cart;
import org.fasttrackit.onlineshopapi.domain.Customer;
import org.fasttrackit.onlineshopapi.domain.exception.ResourceNotFoundException;
import org.fasttrackit.onlineshopapi.service.CartService;
import org.fasttrackit.onlineshopapi.setps.CustomerSteps;
import org.fasttrackit.onlineshopapi.transfer.cart.AddProductToCartRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.ws.Action;
import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartServiceIntergrationTests {

	@Autowired
	private CustomerSteps customerSteps;

	@Autowired
	private CartService cartService;

	@Test
	public void testAddToCart_whenValidRequest_thenReturnCart() throws ResourceNotFoundException {
		Customer customer = customerSteps.testCreateCustomerMethod();

		AddProductToCartRequest request = new AddProductToCartRequest();
		request.setCustomerId(customer.getId());

		// todo: create product relato...
		request.setProductIds(Collections.singleton(1L));

		Cart cart = cartService.addProductToCart(request);

	}

}
