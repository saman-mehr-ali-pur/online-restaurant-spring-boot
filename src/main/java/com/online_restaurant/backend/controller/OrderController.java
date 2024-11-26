package com.online_restaurant.backend.controller;

import com.online_restaurant.backend.model.Order;
import com.online_restaurant.backend.model.User;
import com.online_restaurant.backend.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @PostMapping("/save")
    public Order save(@RequestBody Order order){
        System.out.println(order);
        orderService.save(order);
        System.out.println(order);
        return order;
    }

    @GetMapping("/getAll")
    public List<Order> getAll(){
        return orderService.get();
    }

    @GetMapping("/get/{userId}")
    public List<Order> getByUser(@PathVariable("userId") int id){
        User user= new User();
        user.setId(id);
        return orderService.get(user);
    }

    @DeleteMapping("/delete/{id}")
    public void removeOrder(@PathVariable("id") int id){
        Order order = new Order();
        order.setId(id);
        orderService.remove(order);
    }

}
