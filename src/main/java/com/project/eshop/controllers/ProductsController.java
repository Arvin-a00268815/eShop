package com.project.eshop.controllers;

import com.project.eshop.model.Item;
import com.project.eshop.repository.ItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("products")
public class ProductsController {

    @Autowired
    ItemsRepository repository;

    @GetMapping("/test")
    public String test(){
        return "test-api";
    }

    @GetMapping("/getItems")
    public List<Item> getProducts(){
        return repository.findAll();
    }

    @PostMapping("/addItem")
    public void addItem(@RequestBody Item item){
        repository.save(item);
    }

}
