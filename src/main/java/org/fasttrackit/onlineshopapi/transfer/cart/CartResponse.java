package org.fasttrackit.onlineshopapi.transfer.cart;

import org.fasttrackit.onlineshopapi.transfer.product.ProductResponse;

import java.util.Set;

public class CartResponse {

    private long id;
    private Set<ProductResponse> products;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<ProductResponse> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductResponse> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "CartResponse{" +
                "id=" + id +
                ", products=" + products +
                '}';
    }
}
