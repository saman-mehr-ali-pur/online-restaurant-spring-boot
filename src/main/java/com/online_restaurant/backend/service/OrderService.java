package com.online_restaurant.backend.service;

import com.online_restaurant.backend.model.Order;
import com.online_restaurant.backend.repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class OrderService implements BaseService<Order>{

    @Autowired
    private OrderRepo orderRepo;

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
}
