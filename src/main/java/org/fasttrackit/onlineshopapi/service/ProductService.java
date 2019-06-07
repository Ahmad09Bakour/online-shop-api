package org.fasttrackit.onlineshopapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fasttrackit.onlineshopapi.domain.Product;
import org.fasttrackit.onlineshopapi.domain.exception.ResourceNotFoundException;
import org.fasttrackit.onlineshopapi.persistence.ProductRepository;
import org.fasttrackit.onlineshopapi.transfer.product.CreateProductRequest;
import org.fasttrackit.onlineshopapi.transfer.product.GetProductsRequeset;
import org.fasttrackit.onlineshopapi.transfer.product.ProductResponse;
import org.fasttrackit.onlineshopapi.transfer.product.UpdateProductRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service  // we're telling boot spring that this class is a service class
public class ProductService {


    // the following block of codes are about storing the objects of classes in boot spring container to be able to use it in the whole project without the need to inherit or make any connection between classes for that reason...
    // boot spring is like a collection of all variables. parameters, methods.... of the project that we can freely use everywhere we want
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class); // we're using LOGGER instead of sout to get messages and know about all processes of our code
    private final ProductRepository productRepository;     // we needed to make it as a constructor to be able to use the @Autowired of boot spring
    private final ObjectMapper objectMapper;  // we're doing the same thing with ObjectMapper to be able to use it with the boot spring

    // we're giving boot spring the control of these objects that we're storing there, which called (IoC)
    // an example of abstraction is what boot spring is doing when we want to create a table for example (that it creates it by itself, without the need to create by ourselves).
    // Inversion Of Control (IoC) and Dependency injection
    @Autowired
    public ProductService(ProductRepository productRepository, ObjectMapper objectMapper) {
        this.productRepository = productRepository;
        this.objectMapper = objectMapper;
    }

    public Product createProduct(CreateProductRequest request) {

        LOGGER.info("Creating product {}", request);
        Product product = objectMapper.convertValue(request, Product.class);// we're converting the type of request to become like the type of Product objects. instead of going with whole process (product.setQuantity(request.getQuantity());...)
        product.setQuantity(request.getQuantity());
        return productRepository.save(product);
    }

    public Product updateProduct (long id, UpdateProductRequest request) throws ResourceNotFoundException {
        LOGGER.info("Updating product {} {}", id, request);
        Product product = getProduct(id);

        BeanUtils.copyProperties(request, product);

        return productRepository.save(product);
    }

    public Product getProduct(long id) throws ResourceNotFoundException {
        LOGGER.info("Retrieving product {}", id);

        //using optional objects
        return productRepository.findById(id)
                //lambda expression (since Java 8) anonymous method that has nothing in () which returns what we tells
        .orElseThrow(() -> new ResourceNotFoundException("Product " + id + " does not exist."));
    }

    public  void deleteProduct(long id){
        LOGGER.info("Deleting product {}", id);

        productRepository.deleteById(id);
        LOGGER.info("Deleted product {}", id);
    }

    public Page<ProductResponse> getProducts(GetProductsRequeset request, Pageable pageable){

        LOGGER.info("Retrieving products -> {}", request);
        Page<Product> products;

        if(request.getPartialName() != null && request.getMinimumQuantity() != null){
            products = productRepository.findByNameContainingAndQuantityGreaterThanEqual(request.getPartialName(),
                    request.getMinimumQuantity(), pageable);
        }

        else if (request.getPartialName() != null){
            products  = productRepository.findByNameContaining(request.getPartialName(), pageable);
        }

        else {
            products = productRepository.findAll(pageable);
        }

        // using a DTO "Data Transfer Objects" response to make sure we don't expose properties which are not actually
        // readable of LAZY fetching in Hibernate.
        //Because we used (FetchType:LAZY), which will allow us to get the only info that we need (ex: if we made
        // a relationship with an object, LAZY will allow us to get only the id or what we need from that object, not all
        // it's parameters and what it contains), the opposite implementation occurs when we applied "ajax" code in the
        // web app, ajax will get all the info without ignoring anything about Objects, Relationships...etc.
        // AND FOR THAT we made a LIST of Products(ProductResponse) to separate it from (Page<Product>) which has
        // a relationship with the "Cart" that has a relationship as well with "Customer" that will need us to provide
        // all data that "ajax" won't ignore and ask for("Cross-Origin-Resource-Sharing (CORS)" error)...
        // SO we created a separate List of Products to let "ajax" interact with, AND we added the annotation
        // "CrossOrigin" to the "ProductController" class where added the list there too....
        List<ProductResponse> productResponseList = getProductResponses(products.getContent());
        return new PageImpl<>(productResponseList, pageable, products.getTotalElements());
    }

    public List<ProductResponse> getProductResponses(List<Product> products) {
        List<ProductResponse> productResponseList = new ArrayList<>();
        for (Product product : products){

            // we can't use ObjectMapper because then we're adding the Springboot and we're getting the same error that
            // we're trying to avoid. "Cross-Origin-Resource-Sharing (CORS) error"
            ProductResponse productResponse = new ProductResponse();
            productResponse.setId(product.getId());
            productResponse.setName(product.getName());
            productResponse.setPrice(product.getPrice());
            productResponse.setQuantity(product.getQuantity());

            productResponseList.add(productResponse);
        }
        return productResponseList;
    }
}
