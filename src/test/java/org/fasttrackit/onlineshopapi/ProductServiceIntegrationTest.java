package org.fasttrackit.onlineshopapi;

import org.fasttrackit.onlineshopapi.domain.Product;
import org.fasttrackit.onlineshopapi.domain.exception.ResourceNotFoundException;
import org.fasttrackit.onlineshopapi.service.ProductService;
import org.fasttrackit.onlineshopapi.setps.ProductSteps;
import org.fasttrackit.onlineshopapi.transfer.product.CreateProductRequest;
import org.fasttrackit.onlineshopapi.transfer.product.UpdateProductRequest;
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

public class ProductServiceIntegrationTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductSteps productSteps;

    @Test
    public void testCreateProduct_whenValidRequest_thenReturnStoredProduct(){
        // what we're testing_if the request was valid (success)_ then add the product to the table and store it

        Product product = productSteps.createProductMethod();

    }

    @Test(expected = ConstraintViolationException.class)
    public void testCreateProduct_whenMissingMandatoryDetails_thenThrowConstraintViolationException(){
          // we want to test creating a product_when one or all details are missing (name, id..)_then throw the following exception
        productService.createProduct(new CreateProductRequest());
        Product product = productSteps.createProductMethod();
    }

    @Test
    public void testUpdateProduct_whenValidRequest_thenReturnStoredProduct() throws ResourceNotFoundException {
        Product product = productSteps.createProductMethod();

        product = productService.getProduct(2);
        // todo: finish it

    }

    @Test
    public void testUpdateProduct_whenValidRequest_thenReturnUpdatedProduct() throws ResourceNotFoundException {
        Product product = productSteps.createProductMethod();

        UpdateProductRequest request = new UpdateProductRequest();
        request.setName(product.getName() + "updated");
        request.setQuantity(product.getQuantity() + 10);
        request.setPrice(product.getPrice() + 10);

        Product updatedProduct = productService.updateProduct(product.getId(), request);

        assertThat(updatedProduct, notNullValue());
        assertThat(updatedProduct.getId(), is(product.getId()));
        assertThat(updatedProduct.getName(), is(request.getName()));
        assertThat(updatedProduct.getQuantity(), is(request.getQuantity()));
        assertThat(updatedProduct.getPrice(), is(request.getPrice()));

    }

    // todo: add more tests more scernarios
}
