package com.online_restaurant.backend.controller.api;


import com.online_restaurant.backend.exception.NotFoundException;
import com.online_restaurant.backend.model.Order;
import com.online_restaurant.backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/order")
public class OrderController {


    @Autowired
    private OrderService orderService;


    @GetMapping("/all")
    public List<Order> getAll(@RequestParam("limit") int limit){
        return orderService.getAll(limit);
    }


    @GetMapping("/get/{id}")
    public Order getOrder(@PathVariable("id") int id){
        Order order = orderService.get(id);
        if(order == null){
            throw new NotFoundException("order with id= "+id+" not found");
        }
        return order;
    }


    @PostMapping
    public Order saveOrder(@RequestBody Order order){
        return orderService.save(order);
    }


    @PatchMapping
    public boolean updateOrder(@RequestBody Order order){
        return orderService.update(order);
    }


    @DeleteMapping("delete")
    public boolean deleteOrder(@RequestParam("orderId") int id){
        return orderService.delete(id);
    }

}
