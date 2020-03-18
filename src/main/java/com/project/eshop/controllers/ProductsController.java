package com.project.eshop.controllers;

import com.project.eshop.controllers.interfaces.InventoryRestInterface;
import com.project.eshop.exceptions.ItemNotFoundException;
import com.project.eshop.model.Item;
import com.project.eshop.repository.InventoryRepository;
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
public class ProductsController implements InventoryRestInterface {

    @Autowired
    InventoryRepository repository;

    @GetMapping("/test")
    public String test(){
        return "test-api";
    }

    @GetMapping("/getItems")
    @Override
    public List<Item> getProducts(){
        List<Item> items = repository.findAll();
        if(items.size() > 0){
            return items;
        }
        throw new ItemNotFoundException("The list is empty, please add some items");
    }


    @PostMapping("/addItem")
    @Override
    public ResponseEntity addItem(@RequestBody Item item) {
        repository.save(item);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/getItem/{id}")
    @Override
    public Item findItem(@PathVariable int id){
        Optional<Item> optional = repository.findById(id);

        if(optional.isPresent()){
            return optional.get();
        }
        throw new ItemNotFoundException("The item "+id+" does not exist");
    }



    @DeleteMapping("/removeItem/{id}")
    @Override
    public void removeItem(@PathVariable int id){

        if(repository.existsById(id)) {
            repository.deleteById(id);
        }else{
            throw new ItemNotFoundException("The item "+id+" does not exists");
        }

    }

    @PutMapping("/updateItem")
    @Override
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
