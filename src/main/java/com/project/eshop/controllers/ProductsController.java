package com.project.eshop.controllers;

import com.project.eshop.model.Item;
import com.project.eshop.repository.ItemsRepository;
import com.sun.jndi.toolkit.url.Uri;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/getItem/{id}")
    public Optional<Item> findItem(@PathVariable int id){
        return repository.findById(id);
    }

    @DeleteMapping("/removeItem/{id}")
    public void removeItem(@PathVariable int id){
        repository.deleteById(id);
    }

    @PutMapping("/updateItem")
    public ResponseEntity updateItem(@RequestBody Item item){
        if (repository.existsById(item.getId())) {
            repository.save(item);
            return ResponseEntity.status(HttpStatus.OK).build();
        }else{
            repository.save(item);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}")
                    .buildAndExpand(item.getId()).toUri();
            return ResponseEntity.created(location).build();
        }
    }
}
