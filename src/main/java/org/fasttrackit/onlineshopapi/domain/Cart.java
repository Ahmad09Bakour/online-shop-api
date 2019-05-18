package org.fasttrackit.onlineshopapi.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Cart {

    @Id
    private long id;

    @OneToOne(fetch = FetchType.LAZY) // this
    @MapsId // this annotation is to get the id of the customer only, not all the customer's details....
    private Customer customer;

    // with ManyToMany annotation (fetch = FetchType.LAZY) already added, so we don't need to worry about it
    @ManyToMany(cascade = CascadeType.MERGE) // cascade means what kind of action should be taken to the other side of the relationship
    @JoinTable(name = "cart_product",
    joinColumns = @JoinColumn(name = "cart_id"),
    inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Set<Product> products = new HashSet<>(); // because of HashSet, the Set will never be empty....

    public void addProduct(Product product) { // this logic here is really important to make the  relationship
        // adding the product to the current cart
        products.add(product);
        // adding the current cart to the carts set of the received product
        product.getCarts().add(this);
    }

    public void removeProduct(Product product){ // this logic here is really important to make the  relationship
        products.remove(product);
        product.getCarts().remove(this);
    }

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

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cart cart = (Cart) o;

        if (id != cart.id) return false;
        if (customer != null ? !customer.equals(cart.customer) : cart.customer != null) return false;
        return products != null ? products.equals(cart.products) : cart.products == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (customer != null ? customer.hashCode() : 0);
        result = 31 * result + (products != null ? products.hashCode() : 0);
        return result;
    }
}
