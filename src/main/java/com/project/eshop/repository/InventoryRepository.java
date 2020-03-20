package com.project.eshop.repository;

import com.project.eshop.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Item, Integer> {

    List<Item> findByPriceGreaterThanEqual(int price);
    List<Item> findByColorOrderByPriceDesc(String color);
}
