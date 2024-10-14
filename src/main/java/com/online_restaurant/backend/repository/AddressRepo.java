package com.online_restaurant.backend.repository;


import com.online_restaurant.backend.model.Address;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AddressRepo {


    @Autowired
    private EntityManagerFactory emf;

    public boolean saveAddress(Address address){

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(address);
        em.getTransaction().commit();
        return true;
    }


    public Address find(Address address){

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Address result = (Address) em.createQuery("select ad from Address as ad where ad.getId = :id").setParameter("id",
                address.getId()).getSingleResult();

        em.getTransaction().commit();
        return result;
    }


    public List<Address> getAllAddress(){

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Address> result = em.createQuery("select ad from Address ad ").getResultList();
        em.getTransaction().commit();
        return result;

    }


    public boolean remove(Address address){

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(address);
        em.getTransaction().commit();
        return true;
    }


    public boolean update(Address address){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Address result = em.find(Address.class,address.getId());
        result.setAddress(address.getAddress());
        result.setPostalCode(address.getPostalCode());
        result.setUser(address.getUser());
        em.getTransaction().commit();
        return true;
    }


}
