package com.online_restaurant.backend.service;

import com.online_restaurant.backend.model.Food;
import com.online_restaurant.backend.model.Order;
import com.online_restaurant.backend.model.User;
import com.online_restaurant.backend.repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class OrderService implements BaseService<Order>{

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private UserService userService;

    @Override
    public Order get(int id) {
        return orderRepo.get(id);
    }

    @Override
    public List<Order> getAll(int limit) {
        return orderRepo.getAll(limit);
    }

    @Override
    public Order save(Order ob) {
        return orderRepo.save(ob);
    }

    @Override
    public boolean update(Order ob) {
        return orderRepo.update(ob);
    }

    @Override
    public boolean delete(int id) {
        Order order = new Order();
        order.setId(id);
        return orderRepo.delete(order);
    }


    public boolean addFood(Food food,Order order,int num){
        return orderRepo.addFood(food,order,num);
    }


    public List<Order> getByUser(User user){

        user = userService.get(user);
        return orderRepo.getByUser(user);
    }


    public boolean pay(int id){
        Order order = new Order();
        order.setId(id);
        return orderRepo.pay(order);
    }

    public  boolean removeItem(int foodId,int orderId){
        Order order = new Order();
        order.setId(orderId);
        Food food = new Food();
        food.setId(foodId);

        return orderRepo.removeItem(order,food);
    }
}
