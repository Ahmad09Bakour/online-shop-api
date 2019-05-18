package org.fasttrackit.onlineshopapi.setps;

import org.fasttrackit.onlineshopapi.domain.Product;
import org.fasttrackit.onlineshopapi.service.ProductService;
import org.fasttrackit.onlineshopapi.transfer.product.CreateProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

@Component
public class ProductSteps {
    @Autowired
    private ProductService productService;  // we can't created a Product object by "Product product = ...", and instead
    // call ProductService and create a product object through it ...

    public Product createProductMethod() {
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

        return product;
    }
}
