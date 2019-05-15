package org.fasttrackit.onlineshopapi.domain;

import javax.persistence.*;

@Entity
public class Cart {

    @Id
    private long id;

    @OneToOne(fetch = FetchType.LAZY) // this
    @MapsId // this annotation is to get the id of the customer only, not all the customer's details....
    private Customer customer;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
