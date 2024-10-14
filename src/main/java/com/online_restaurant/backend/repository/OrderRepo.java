package com.online_restaurant.backend.repository;


import com.online_restaurant.backend.model.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepo {


    @Autowired
    private EntityManagerFactory emf;

    public void save(Order order){

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(order);
        em.getTransaction().commit();

    }

    public Order getById(Order order){
        EntityManager em = emf.createEntityManager();
        Order result = em.find(Order.class,order.getId());
        em.getTransaction().commit();
        return result;

    }

}
