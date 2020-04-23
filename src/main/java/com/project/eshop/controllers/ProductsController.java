package com.project.eshop.controllers;

import com.project.eshop.controllers.interfaces.InventoryRestInterface;
import com.project.eshop.exceptions.ItemNotFoundException;
import com.project.eshop.model.Item;
import com.project.eshop.model.Order;
import com.project.eshop.repository.InventoryRepository;
import com.project.eshop.repository.OrderRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("inventory")
@Api(value = "InventoryRestController", description = "REST APIs related to products and orders")

public class ProductsController implements InventoryRestInterface {

    @Autowired
    InventoryRepository inventoryRepository;
    @Autowired
    OrderRepository orderRepository;

    @GetMapping("test")
    public String test(){
        return "test-api";
    }

    @GetMapping("products/")
    @ApiOperation(value = "Get list of products from the inventory", response = Page.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 404, message = "No products found!!!") })
    @Override
    public Page<Item> getProducts(@RequestParam Optional<Integer> page,
                                  @RequestParam Optional<Integer> sortKey){

        int pageIndex = 0;
        Pageable pageable = null;

        if(page.isPresent()){
            pageIndex = page.get();
        }

        if (sortKey.isPresent()){

            int key = sortKey.get();
            switch (key){
                case 0:
                    pageable = PageRequest.of(pageIndex, 5, Sort.by("price"));
                    break;
                case 1:
                    pageable = PageRequest.of(pageIndex, 5, Sort.by("price").descending());

                    break;
                case 2:
                    pageable = PageRequest.of(pageIndex, 5, Sort.by("rating").descending());

                    break;
                case 3:
                    pageable = PageRequest.of(pageIndex, 5, Sort.by("rating"));

                    break;
                default:
                    pageable = PageRequest.of(pageIndex,5);
                    break;
            }
        }else {
            pageable = PageRequest.of(pageIndex,5);
        }
        Page<Item> items = inventoryRepository.findAll(pageable);
        if(items.getTotalElements() > 0){
            return items;
        }
        throw new ItemNotFoundException("The list is empty, please add some items");
    }


    @PostMapping("products/")
    @ApiOperation(value = "Add product to the inventory", response = Page.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK")})
    @Override
    public ResponseEntity addItem(@RequestBody Item item) {


        item.setId(null);
        Item savedItem = inventoryRepository.save(item);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}")
                .buildAndExpand(savedItem.getId()).toUri();
        return ResponseEntity.created(location).build();

    }

    @PutMapping("products/")
    @ApiOperation(value = "Update the existing product if present in the inventory", response = Page.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK")})
    @Override
    public ResponseEntity updateItem(@RequestBody Item item){

        if (inventoryRepository.existsById(item.getId())) {
            inventoryRepository.save(item);
            return ResponseEntity.status(HttpStatus.OK).build();
        }else{
            inventoryRepository.save(item);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}")
                    .buildAndExpand(item.getId()).toUri();
            return ResponseEntity.created(location).build();
        }
    }

    @GetMapping("products/{id}")
    @ApiOperation(value = "Search for the product using product id", response = Page.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 404, message = "No product found!!!") })
    @Override
    public Item findItem(@PathVariable int id){
        Optional<Item> optional = inventoryRepository.findById(id);

        if(optional.isPresent()){
            return optional.get();
        }
        throw new ItemNotFoundException("The item "+id+" does not exist");
    }



    @DeleteMapping("products/{id}")
    @ApiOperation(value = "Removes the particular product from the inventory", response = Page.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 404, message = "No product found!!!") })
    @Override
    public void removeItem(@PathVariable int id){

        if(inventoryRepository.existsById(id)) {
            inventoryRepository.deleteById(id);
        }else{
            throw new ItemNotFoundException("The item "+id+" does not exists");
        }

    }


    @GetMapping("products/price/greater/{price}")
    @ApiOperation(value = "Get list of products from the inventory for which the price is greater than the given price", response = Page.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 404, message = "No products found!!!") })
    @Override
    public List<Item> findByPriceGreaterThanEqual(@PathVariable int price) {

        List<Item> items = inventoryRepository.findByPriceGreaterThanEqual(price);

        if(items.size() > 0) {
            return items;
        }
        throw  new ItemNotFoundException("No match found");
    }

    @GetMapping("products/orderByPrice/color/{color}")
    @ApiOperation(value = "Get list of products using the color but order by price", response = Page.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 404, message = "No products found!!!") })
    @Override
    public List<Item> findByColorOrderByPrice(@PathVariable String color) {

        List<Item> items = inventoryRepository.findByColorOrderByPriceDesc(color);
        return items;
    }

    @GetMapping("orders/")
    @ApiOperation(value = "Get list of all orders made by the customers", response = Page.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 404, message = "No orders found!!!") })
    public List<Order> getOrders(){
        return orderRepository.findAll();
    }

    @PostMapping("orders/")
    @ApiOperation(value = "Insert the order details made by the customer", response = Page.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK") })
    public ResponseEntity addOrder(@RequestBody Order order) {

        order.setId(null);
        Order savedItem = orderRepository.save(order);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}")
                .buildAndExpand(savedItem.getId()).toUri();
        return ResponseEntity.created(location).build();

    }
}
