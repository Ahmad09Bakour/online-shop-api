package org.fasttrackit.onlineshopapi;

import org.fasttrackit.onlineshopapi.domain.Product;
import org.fasttrackit.onlineshopapi.service.ProductService;
import org.fasttrackit.onlineshopapi.transfer.CreateProductRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest

public class ProductServiceIntegrationTest {

    @Autowired
    private ProductService productService;

    @Test
    public void testCreateProduct_whenValidRequest_thenReturnStoredProduct(){
        // what we're testing_

        CreateProductRequest request = new CreateProductRequest();
        request.setName("Shoes");
        request.setPrice(99.99);
        request.setQuantity(100);

        Product product = productService.createProduct(request);

        assertThat("No product returned", product, notNullValue()); // give me an error with this massage that we want to get once the object "product" when the following condition doesn't occur
        assertThat("Product does not have an auto-generated id", product.getId(), greaterThan(0L));
        assertThat(product.getName(), is(request.getName())); // no massage, but give me an error once the name of the product doesn't match with the request
        assertThat(product.getPrice(), is(request.getPrice()));
        assertThat(product.getQuantity(), is(request.getQuantity()));

    }

    @Test(expected = ConstraintViolationException.class)
    public void testCreateProduct_whenMissingMandatoryDetails_thenThrowConstraintViolationException(){

        productService.createProduct(new CreateProductRequest());
    }
}
