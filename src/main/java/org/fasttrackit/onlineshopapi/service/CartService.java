package org.fasttrackit.onlineshopapi.service;

import org.fasttrackit.onlineshopapi.domain.Cart;
import org.fasttrackit.onlineshopapi.domain.Customer;
import org.fasttrackit.onlineshopapi.domain.Product;
import org.fasttrackit.onlineshopapi.domain.exception.ResourceNotFoundException;
import org.fasttrackit.onlineshopapi.persistence.CartRepository;
import org.fasttrackit.onlineshopapi.transfer.cart.AddProductToCartRequest;
import org.fasttrackit.onlineshopapi.transfer.cart.CartResponse;
import org.fasttrackit.onlineshopapi.transfer.product.ProductResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class CartService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartService.class);

    private final CartRepository cartRepository;  // first step, get logic from Repos. we make it final because we don't
    // want to get anything back, data enters only once , which will require us to make a constructor out of it as shown below.
    private final CustomerService customerService; // second step , connect Cart with Customer (each customer must have one cart)
    // the same story with the first static method
    private final ProductService productService;

    @Autowired // make for the static methods up constructors by intelliJ help :))
    public CartService(CartRepository cartRepository, CustomerService customerService, ProductService productService) {
        this.cartRepository = cartRepository;
        this.customerService = customerService;
        this.productService = productService;
    }

    @Transactional
    // annotation that will require multiple objects (such as Product, Customer...) to be added to the same
    // method and as a result from their logic connection that must be perfectly successful. otherwise, this annotation
    // will leave everything Unchanged.
    // we created CartResponse class for the same reason with ProductResponse, Go ProductService for more info
    public CartResponse addProductToCart(AddProductToCartRequest request) throws ResourceNotFoundException {
        //customer needs to be retrieved from the db so it's added on Hibernate's session
        // we could've typed: Customer customer = new Customer >> request=customer.getId();
        // but the annotation won't allow that, because we need to call the main Customer object from the service to
        // to implement the required process with their relationships
        Customer customer = customerService.getCustomer(request.getCustomerId());

        Cart cart = new Cart();
        cart.setCustomer(customer);

        for (Long id : request.getProductIds()) {
            Product product = productService.getProduct(id);
            cart.addProduct(product); // adding he product that we jsut created
        }

        Cart savedCart = cartRepository.save(cart);

        List<ProductResponse> productResponses =
                productService.getProductResponses(new ArrayList<>(savedCart.getProducts()));

        CartResponse cartResponse = new CartResponse();
        cartResponse.setId(savedCart.getId()); // savedCart not cart object
        cartResponse.setProducts(new HashSet<>(productResponses)); // convert again

        return cartResponse;

    }

}
