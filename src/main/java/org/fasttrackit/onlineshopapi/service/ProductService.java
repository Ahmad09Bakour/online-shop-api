package org.fasttrackit.onlineshopapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fasttrackit.onlineshopapi.domain.Product;
import org.fasttrackit.onlineshopapi.persistence.ProductRepository;
import org.fasttrackit.onlineshopapi.transfer.CreateProductRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
