package com.online_restaurant.backend.controller.api;

import com.online_restaurant.backend.model.Order;
import com.online_restaurant.backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/food")
public class FoodController {

    @Autowired
    private OrderService orderService;


    @GetMapping("/all")
    public List<Order> getAllOrders(@RequestParam("limit") int limit){
        return orderService.getAll(limit);
    }

    @GetMapping("/get/{id}")
    public Order get(@PathVariable("id") int id){
        return orderService.get(id);
    }


    @PostMapping
    public Order save(@RequestBody Order order){
       return orderService.save(order);
    }


    @DeleteMapping("/delete/{id}")
    public boolean deleteOrder(@PathVariable("id") int id){
        return orderService.delete(id);
    }



    @PostMapping("update")
    public boolean updateOrder(@RequestBody Order order){
        return orderService.update(order);
    }

}
