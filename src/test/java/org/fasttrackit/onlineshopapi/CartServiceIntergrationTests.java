package org.fasttrackit.onlineshopapi;

import org.fasttrackit.onlineshopapi.domain.Cart;
import org.fasttrackit.onlineshopapi.domain.Customer;
import org.fasttrackit.onlineshopapi.domain.Product;
import org.fasttrackit.onlineshopapi.domain.exception.ResourceNotFoundException;
import org.fasttrackit.onlineshopapi.service.CartService;
import org.fasttrackit.onlineshopapi.setps.CustomerSteps;
import org.fasttrackit.onlineshopapi.setps.ProductSteps;
import org.fasttrackit.onlineshopapi.transfer.cart.AddProductToCartRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartServiceIntergrationTests {

	@Autowired
	private CustomerSteps customerSteps;

	@Autowired
	private ProductSteps productSteps; // because ProductSteps is an component is SpringBoot container, we can call it here
	// with the notation Autowired to represent "createCustomerMethod" that will allow us continue our test faster

	@Autowired
	private CartService cartService; // we can't create a Cart object like"Cart cart = new Cart" here, because
	//and to do that we need the CartService which will allow us to do that easily

	@Test
	public void testAddToCart_whenValidRequest_thenReturnCart() throws ResourceNotFoundException {
		Customer customer = customerSteps.createCustomerMethod();

		AddProductToCartRequest request = new AddProductToCartRequest();
		request.setCustomerId(customer.getId());

		Product product = productSteps.createProductMethod();
		request.setProductIds(Collections.singleton(product.getId()));

		Cart cart = cartService.addProductToCart(request);

		assertThat(cart, notNullValue()); // actual result and the expected result... we get the "cart" and it supposed to be notNullValue like want to
		assertThat(customer.getId(), is(cart.getId()));
		assertThat(cart.getProducts(), hasSize(greaterThan(0)));//first we need to make sure that the Set is not empty and has a size
		// Set doesn't have a get method like lists
		//using iterator().next() to go element by element through the Set
		Product productFromCart = cart.getProducts().iterator().next(); // we store this logic in a variable because it will be long enough to complicate things
		assertThat(productFromCart, notNullValue()); //second we need to make sure
		assertThat(productFromCart.getId(), is(product.getId())); // and the last thing is to see if the id of the card is stored in the Cart

	}

}
