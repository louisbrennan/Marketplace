package com.example.marketplace.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany (
            mappedBy = "basket",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<BasketItem> items;
    public long getId() { return id; }


    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public List<BasketItem> getItems() { return items; }
    public void setItems(List<BasketItem> items) { this.items = items; }
}

