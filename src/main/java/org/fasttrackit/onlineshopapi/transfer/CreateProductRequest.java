package org.fasttrackit.onlineshopapi.transfer;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CreateProductRequest {

    @NotNull
    @Min(0)
    private double price;

    @NotNull
    @Min(0)
    private int quantity;

    @NotNull
    private String name;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CreateProductRequest{" +
                "price=" + price +
                ", quantity=" + quantity +
                ", name='" + name + '\'' +
                '}';
    }
}
