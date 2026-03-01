package com.example.marketplace.repository;

import com.example.marketplace.model.Basket;
import com.example.marketplace.model.BasketItem;
import com.example.marketplace.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BasketItemRepository extends JpaRepository<BasketItem, Long> {
    public Optional<BasketItem> findByProductAndBasket(Product product, Basket basket);
}
