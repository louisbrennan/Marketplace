package com.example.marketplace.repository;

import com.example.marketplace.model.Basket;
import com.example.marketplace.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BasketRepository extends JpaRepository<Basket, Long> {
    public Optional<Basket> findByUser(User user);
}
