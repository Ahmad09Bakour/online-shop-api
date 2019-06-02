package org.fasttrackit.onlineshopapi.web;


import org.fasttrackit.onlineshopapi.domain.Product;
import org.fasttrackit.onlineshopapi.domain.exception.ResourceNotFoundException;
import org.fasttrackit.onlineshopapi.service.ProductService;
import org.fasttrackit.onlineshopapi.transfer.product.CreateProductRequest;
import org.fasttrackit.onlineshopapi.transfer.product.GetProductsRequeset;
import org.fasttrackit.onlineshopapi.transfer.product.ProductResponse;
import org.fasttrackit.onlineshopapi.transfer.product.UpdateProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController     // this  should be added for the controller class(the servlet) for spring boot to work
@RequestMapping("/products")
@CrossOrigin
public class ProductController {    // this class represents the servlet

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    // RequestBody is like mapper (spring boot will translate the response that is coming form the service in a query form to Product class's type
    // valid createProductRequest (it has long(id), string(name)....
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody @Valid CreateProductRequest request){

        Product response = productService.createProduct(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") long id) throws ResourceNotFoundException {
        Product response = productService.getProduct(id);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
//    @PutMapping("/{id}")
    @RequestMapping(method = RequestMethod.PUT, path = "/{id}")
    public ResponseEntity updateProduct(@PathVariable("id") long id, @RequestBody @Valid UpdateProductRequest request) throws ResourceNotFoundException {

        productService.updateProduct(id, request);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable("id") long id){
        productService.deleteProduct(id);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getProducts(@Valid GetProductsRequeset request, Pageable pageable){

        Page<ProductResponse> response = productService.getProducts(request, pageable);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
