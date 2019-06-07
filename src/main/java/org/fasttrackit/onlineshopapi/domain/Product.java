package org.fasttrackit.onlineshopapi.domain;

import com.sun.javafx.beans.IDProperty;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity    // we're telling boot spring that this class is going to be a table, that will create it automatically
public class Product {

    @Id  // we're telling boot spring to make the following property a Primary Key in the table that we're creating
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // auto increment   ......(for one table)
    private long id;

    @NotNull
    @Min(0)
    private double price;

    @NotNull
    @Min(0)
    private int quantity;

    @NotNull
    private String name;

    @ManyToMany(mappedBy = "products") // mappedBy means: Product is in a relationship with "Cart" (ManyToMany) which Cart class controlled it
    private Set<Cart> carts = new HashSet<>(); // because of HashSet, the Set will never be empty


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public Set<Cart> getCarts() {
        return carts;
    }

    public void setCarts(Set<Cart> carts) {
        this.carts = carts;
    }

    @Override // when we add equals() and hashCode() for the Product elements, we should skip the Cart element,
    // otherwise it will run an infinite loop when we run the app
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (id != product.id) return false;
        if (Double.compare(product.price, price) != 0) return false;
        if (quantity != product.quantity) return false;
        return name != null ? name.equals(product.name) : product.name == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (id ^ (id >>> 32));
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + quantity;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
