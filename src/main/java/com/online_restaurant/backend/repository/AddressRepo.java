package com.online_restaurant.backend.repository;


import com.online_restaurant.backend.model.Address;
import com.online_restaurant.backend.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AddressRepo {


    @Autowired
    private EntityManagerFactory emf;

    public void saveAddress(Address address){

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(address);
        em.getTransaction().commit();
//        return true;
    }


    public Address find(Address address){

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Address result = (Address) em.createQuery("select ad from Address as ad where ad.id = :id").setParameter("id",
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



    public List<Address> getAllAddress(User user) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Address> result = em.createQuery(
                        "SELECT ad FROM Address ad WHERE ad.user = :user", Address.class)
                .setParameter("user", user)
                .getResultList();
        em.getTransaction().commit();
        em.close();

        result.stream().forEach( item -> {item.
               setUser(null);});
        return result;
    }


    public boolean remove(Address address){

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        address = em.find(Address.class,address.getId());
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
