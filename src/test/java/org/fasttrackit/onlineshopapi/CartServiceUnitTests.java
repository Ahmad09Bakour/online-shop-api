package org.fasttrackit.onlineshopapi;

import org.fasttrackit.onlineshopapi.domain.Cart;
import org.fasttrackit.onlineshopapi.domain.Customer;
import org.fasttrackit.onlineshopapi.domain.Product;
import org.fasttrackit.onlineshopapi.domain.exception.ResourceNotFoundException;
import org.fasttrackit.onlineshopapi.persistence.CartRepository;
import org.fasttrackit.onlineshopapi.service.CartService;
import org.fasttrackit.onlineshopapi.service.CustomerService;
import org.fasttrackit.onlineshopapi.service.ProductService;
import org.fasttrackit.onlineshopapi.transfer.cart.AddProductToCartRequest;
import org.fasttrackit.onlineshopapi.transfer.cart.CartResponse;
import org.fasttrackit.onlineshopapi.transfer.product.ProductResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class) //with this annotation we're telling spring boot that we're using "Mock.." class
public class CartServiceUnitTests {// which we need to specify what elements we want to mock so it doesn't do anything with
    // IOC container for the following elements

    @Mock
    private CartRepository cartRepository;
    @Mock
    private ProductService productService;
    @Mock
    private CustomerService customerService;

    private CartService cartService;

    // we can use @BeforeClass annotation which will run only once for all tests in the class (One per class)
    @Before // we're using this annotation which will run just before running this following test. (Many per test, many per Class)
    public void setup(){
        cartService = new CartService(cartRepository, customerService, productService);
    }
    @Test
    public void testAddProductToCart_whenValidRequest_theReturnCart() throws ResourceNotFoundException {

        // to create a Cart object we need to create the following objects:
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("mockName");

        // we're mocking the "customerService" and we're not calling the real one, we're assuming by "Mock" annotation
        // that the customerService is running well, and we only want to test the logic in "addProductToCart" class
        when(customerService.getCustomer(any(Long.class))).thenReturn(customer);
        // we're saying here that whenever the customerService(the fake one that we just created) is calling
        // "getCustomer", then respond to that by any Long object (which will be a number) and then return the fake
        // customer object that we just created.

        Product product = new Product();
        product.setId(2L);
        product.setName("mockProduct");
        product.setPrice(10);
        product.setQuantity(100);

        when(productService.getProduct(any(Long.class))).thenReturn(product);

        Cart cart = new Cart();
        cart.setId(customer.getId());
        cart.setCustomer(customer);
        cart.addProduct(product);

        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        // after setting the dependencies of "addProductToCart()" method, then we need to go with the next
        // method from

        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setPrice(product.getPrice());
        productResponse.setQuantity(product.getQuantity());

        when(productService.getProductResponses(anyList())).thenReturn(Collections.singletonList(productResponse));
        //by using the keyword "singletonList", we're telling it to return an immutable list containing only the
        // specified object "productResponse" that we just created

        AddProductToCartRequest addProductToCartRequest = new AddProductToCartRequest();
        addProductToCartRequest.setCustomerId(customer.getId());
        addProductToCartRequest.setProductIds(Collections.singleton(product.getId()));
        //by using the keyword "singleton", we're telling it to return the object "productResponse" only that we just created

        CartResponse cartResponse = cartService.addProductToCart(addProductToCartRequest);

        // after we created the dependencies for the Class, now we need to add assertions for this test, by 2 steps

        // step 1, is the normal assertions that we used to add for the integration tests
        assertThat(cartResponse, notNullValue());
        assertThat(customer.getId(), is(cartResponse.getId()));
        assertThat(cartResponse.getProducts(), hasSize(greaterThan(0)));

        ProductResponse productFromCart = cartResponse.getProducts().iterator().next();
        assertThat(productFromCart, notNullValue());
        assertThat(productFromCart.getId(), is(product.getId()));

        // step 2, is verifying that these special conditions that we sat are met or not.
        verify(customerService).getCustomer(any(Long.class));
        verify(productService).getProduct(any(Long.class));
        verify(cartRepository).save(any(Cart.class));
        verify(productService).getProductResponses(anyList());


    }
}
