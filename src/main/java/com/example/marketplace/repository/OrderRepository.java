package com.example.marketplace.repository;

import com.example.marketplace.model.OrderItem;
import com.example.marketplace.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderItem, Long> {
    public Optional<List<OrderItem>> findByUser(User user);
}
