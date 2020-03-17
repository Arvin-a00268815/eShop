package com.project.eshop.repository;

import com.project.eshop.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemsRepository extends JpaRepository<Item, Integer> {

}
