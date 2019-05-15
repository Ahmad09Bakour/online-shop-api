package org.fasttrackit.onlineshopapi.transfer.cart;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

public class AddProductToCartRequest {

    @NotNull
    private long customerId;

    @NotNull
    @NotEmpty
    private Set<Long> productIds; // Set is a list of unique items... no duplication. I can have a product with an id
    // but I can't have the same product with another id, I can change the quantity of the product, or some of its attributes

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public Set<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(Set<Long> productIds) {
        this.productIds = productIds;
    }

    @Override
    public String toString() {
        return "AddProductToCartRequest{" +
                "customerId=" + customerId +
                ", productIds=" + productIds +
                '}';
    }
}
