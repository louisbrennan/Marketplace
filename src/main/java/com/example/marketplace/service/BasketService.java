package com.example.marketplace.service;

import com.example.marketplace.model.*;
import com.example.marketplace.repository.BasketItemRepository;
import com.example.marketplace.repository.BasketRepository;
import com.example.marketplace.repository.OrderRepository;
import org.hibernate.query.Order;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
public class BasketService {
    private final BasketRepository basketRepository;
    private final BasketItemRepository basketItemRepository;
    private final OrderRepository orderRepository;

    public BasketService(BasketRepository basketRepository,
                         BasketItemRepository basketItemRepository,
                         OrderRepository orderRepository) {
        this.basketRepository = basketRepository;
        this.basketItemRepository = basketItemRepository;
        this.orderRepository = orderRepository;
    }

    public Basket getBasket(User user) {
        return basketRepository.findByUser(user).orElseGet(() -> {
            Basket b = new Basket();
            b.setUser(user);
            return basketRepository.save(b);
        });
    }

    public void addToBasket(Product product, User user) {
        Basket basket = getBasket(user);
        BasketItem item = basketItemRepository.findByProductAndBasket(product, basket).orElse(null);

        if (item != null) {
            item.setQuantity(item.getQuantity() + 1);
            basketItemRepository.save(item);
        } else {
            item = new BasketItem(basket, product);
            basketItemRepository.save(item);
        }
        basketRepository.save(basket);
    }

    public void removeFromBasket(Product product, User user) {
        Basket basket = getBasket(user);
        BasketItem item = basketItemRepository.findByProductAndBasket(product, basket).orElse(null);
        if (item == null) { return; }
        if (item.getQuantity() >= 2) {
            item.setQuantity(item.getQuantity() - 1);
            basketItemRepository.save(item);
        } else {
            basketItemRepository.delete(item);
        }
        basketRepository.save(basket);
    }

    public void removeAllFromBasket(Product product, User user) {
        Basket basket = getBasket(user);
        BasketItem item = basketItemRepository.findByProductAndBasket(product, basket).orElse(null);
        if (item != null) {
            basketItemRepository.delete(item);
        }
    }

    public void clearBasket(User user) {
        Basket basket = getBasket(user);
        basket.getItems().clear();
        basketRepository.save(basket);
    }

    public void basketToOrder(User user) {
        Basket basket = getBasket(user);

        for (BasketItem item : basket.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(item.getProduct());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPriceAtPurchase(item.getProduct().getPrice());
            orderItem.setDateTime(ZonedDateTime.now());
            orderItem.setUser(user);

            orderRepository.save(orderItem);
        }
    }
}
