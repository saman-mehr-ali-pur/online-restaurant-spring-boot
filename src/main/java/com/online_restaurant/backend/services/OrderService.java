package com.online_restaurant.backend.services;

import com.online_restaurant.backend.model.Order;
import com.online_restaurant.backend.model.User;
import com.online_restaurant.backend.repository.OrderRepo;
import com.online_restaurant.backend.repository.PaymentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo ;

    public void save(Order order){
         orderRepo.save(order);
    }

    public List<Order> get(){
        return orderRepo.getAll();
    }
    public Order get(Order order){
        return orderRepo.getById(order);
    }

    public List<Order> get(User user){
        return orderRepo.getByUser(user);
    }

    public void remove(Order order){
        orderRepo.remove(order);
    }

}
