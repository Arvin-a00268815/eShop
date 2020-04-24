package com.project.eshop.controllers.interfaces;

import com.project.eshop.model.Item;
import com.project.eshop.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

public interface InventoryRestInterface {

     ResponseEntity addItem(Item item);
     Page<Item> getProducts(Optional<Integer> page, Optional<Integer> sortKey);
     Item findItem(int id);
     void removeItem(int id);
     ResponseEntity updateItem(Item item);
     List<Item> findByPriceGreaterThanEqual(int price);
     List<Item> findByColorOrderByPrice(String color);
     List<Order> getOrders();
     ResponseEntity addOrder(Order order);
     Order findOrder(int id);
}
