package org.fasttrackit.onlineshopapi.web;

import org.fasttrackit.onlineshopapi.domain.exception.ResourceNotFoundException;
import org.fasttrackit.onlineshopapi.service.CartService;
import org.fasttrackit.onlineshopapi.transfer.cart.AddProductToCartRequest;
import org.fasttrackit.onlineshopapi.transfer.cart.CartResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/carts")
@CrossOrigin // this annotation is to handle the error "Cross-Origin-Resource-Sharing (CORS)" which we created CartResponse Class for
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<CartResponse> createCart(@RequestBody @Valid AddProductToCartRequest request) throws ResourceNotFoundException {
        CartResponse cart = cartService.addProductToCart(request);

        return new ResponseEntity<>(cart, HttpStatus.CREATED);
    }
}
