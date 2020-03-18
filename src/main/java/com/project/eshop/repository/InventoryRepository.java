package com.project.eshop.repository;

import com.project.eshop.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Item, Integer> {

}
