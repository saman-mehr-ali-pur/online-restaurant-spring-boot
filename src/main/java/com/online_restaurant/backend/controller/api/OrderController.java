package com.online_restaurant.backend.controller.api;


import com.online_restaurant.backend.exception.NotFoundException;
import com.online_restaurant.backend.model.Food;
import com.online_restaurant.backend.model.Order;
import com.online_restaurant.backend.model.User;
import com.online_restaurant.backend.service.OrderService;
import com.online_restaurant.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@CrossOrigin("*")
public class OrderController {


    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;


    @GetMapping("/all")
    public List<Order> getAll(@RequestParam("limit") int limit){
        System.out.println(limit);
        return orderService.getAll(limit);
    }


    @GetMapping("/get/{id}")
    public Order getOrder(@PathVariable("id") int id){
//        System.out.println("id: "+id);
        Order order = orderService.get(id);
//        System.out.println(order);
        if(order == null){
            throw new NotFoundException("order with id= "+id+" not found");
        }
        return order;
    }


    @PostMapping
    public Order saveOrder(@RequestBody Order order){
        System.out.println(order);

        order.getCustomer().setId(userService.get(order.getCustomer()).getId());
        return orderService.save(order);
    }


    @PatchMapping
    public boolean updateOrder(@RequestBody Order order){
        return orderService.update(order);
    }


    @DeleteMapping("delete/{id}")
    public boolean deleteOrder(@PathVariable("id") int id){
        return orderService.delete(id);
    }


    @GetMapping("add/{foodId}/{orderId}/{num}")
    public boolean addFood(@PathVariable("foodId") int foodId,
                           @PathVariable("orderId") int orderId,
                           @PathVariable("num") int number){
        Order order = new Order();
        order.setId(orderId);
        Food food = new  Food();
        food.setId(foodId);
        return orderService.addFood(food,order,number);
    }


    @GetMapping("/all/user/{username}/{password}")
    public List<Order> getByUser(@PathVariable("username") String username,@PathVariable("password") String password){

        return orderService.getByUser(User.
                                        builder().
                                            username(username).
                                                password(password).
                                                    build());
    }


    @GetMapping("/pay/{id}")
    public boolean pay(@PathVariable("id") int id){
        return orderService.pay(id);
    }

    @DeleteMapping("item/{foodId}/{orderId}")
    public boolean removeItem(@PathVariable("foodId") int foodId,@PathVariable("orderId") int orderId){
        return orderService.removeItem(foodId,orderId);
    }
}
