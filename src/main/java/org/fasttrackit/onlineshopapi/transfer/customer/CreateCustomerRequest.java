package org.fasttrackit.onlineshopapi.transfer.customer;

import javax.validation.constraints.NotNull;

public class CreateCustomerRequest {

    @NotNull
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CreateCustomerRequest{" +
                "name='" + name + '\'' +
                '}';
    }
}
