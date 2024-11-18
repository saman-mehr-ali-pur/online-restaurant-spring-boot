package com.online_restaurant.backend.repository;


import com.online_restaurant.backend.model.Order;
import com.online_restaurant.backend.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderRepo {


    @Autowired
    private EntityManagerFactory emf;

    public void save(Order order){

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(order);
        order = em.find(Order.class,order.getId());
        em.getTransaction().commit();

    }

    public List<Order> getAll(){
        var em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Order> result = em.createQuery("select ord from Order ord",Order.class).getResultList();
        em.getTransaction().commit();
        return result;

    }

    public Order getById(Order order){
        EntityManager em = emf.createEntityManager();
        Order result = em.find(Order.class,order.getId());
        em.getTransaction().commit();
        return result;

    }

    public List<Order> getByUser(User user){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Order> result = em.createQuery("select ord from Order ord where ord.customer = ?1",Order.class).setParameter(1,user).getResultList();
        em.getTransaction().commit();
        result.stream().forEach(item -> {
            item.getFoodList().stream().forEach(food ->{food.setComments(null);});});
        return result;
    }

    public void remove(Order order){
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        order = em.find(Order.class,order.getId());
        em.remove(order);
        em.getTransaction().commit();
        em.close();
    }

}
