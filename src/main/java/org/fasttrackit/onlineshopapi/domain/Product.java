package org.fasttrackit.onlineshopapi.domain;

import com.sun.javafx.beans.IDProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

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
}
