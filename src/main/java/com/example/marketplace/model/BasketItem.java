package com.example.marketplace.model;

import jakarta.persistence.*;

@Entity
public class BasketItem {

    public BasketItem() {
    }

    public BasketItem(Basket basket,  Product product) {
        this.basket = basket;
        this.product = product;
        this.quantity = 1;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private int quantity;

    @ManyToOne ()
    @JoinColumn(name = "basket_id")
    private Basket basket;



    public void setQuantity(int quanity) {this.quantity = quanity;}
    public int getQuantity() {return quantity;}
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    public Basket getBasket() { return basket; }
    public void setBasket(Basket basket) { this.basket = basket; }


}
