package com.example.marketplace.model;

import jakarta.persistence.*;

import java.time.ZonedDateTime;
import java.util.Date;

@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private ZonedDateTime dateTime;
    private double priceAtPurchase;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private int quantity;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public ZonedDateTime getDateTime() { return dateTime; }
    public void setDateTime(ZonedDateTime dateTime) { this.dateTime = dateTime; }
    public double getPriceAtPurchase() { return priceAtPurchase; }
    public void setPriceAtPurchase(double priceAtPurchase) { this.priceAtPurchase = priceAtPurchase; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public int getId() { return id; }


}
