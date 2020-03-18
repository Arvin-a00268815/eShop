package com.project.eshop.controllers.interfaces;

import com.project.eshop.model.Item;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface InventoryRestInterface {

     ResponseEntity addItem(Item item);
     List<Item> getProducts();
     Item findItem(int id);
     void removeItem(int id);
     ResponseEntity updateItem(Item item);
}
