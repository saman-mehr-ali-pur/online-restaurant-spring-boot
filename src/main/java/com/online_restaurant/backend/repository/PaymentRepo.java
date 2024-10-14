package com.online_restaurant.backend.repository;


import com.online_restaurant.backend.model.Payment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PaymentRepo {

    @Autowired
    private EntityManagerFactory emf;



    public void add(Payment payment){

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(payment);
        em.getTransaction().commit();

    }


    public List<Payment> getAll(){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Payment> result = em.createQuery("select p from Payment as p ").getResultList();
        em.getTransaction().commit();
        return result;

    }

    public void  update(Payment payment){

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Payment result = em.find(Payment.class,payment.getId());

        result.setAmount(payment.getAmount());
        result.setStatus(payment.getStatus());
        result.setDatPay(payment.getDatPay());
//        result.setOrder();
        em.getTransaction().commit();

    }


    public void remove(Payment payment){

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(payment);
        em.getTransaction().commit();


    }

}
