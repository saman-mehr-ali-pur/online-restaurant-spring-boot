package com.online_restaurant.backend.repository;


import com.online_restaurant.backend.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Repository
public class UserRepo {


    @Autowired
    private EntityManagerFactory emf;


    public List<User> getAll(){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<User> result = em.createQuery("select u from User u ",User.class).getResultList();
        em.getTransaction().commit();

        return result;
    }


    public User findById(User user){
        EntityManager em  = emf.createEntityManager();

        em.getTransaction().begin();

        User result  = em.find(User.class,user.getId());
        em.getTransaction().commit();
        return result;
    }

    public User findByUsername(User user){
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        User user1 = em.createQuery("select u from User u where u.username= :username",User.class).
                setParameter("username",user.getUsername()).
                getSingleResult();

        em.getTransaction().commit();

        return user1;



    }


    public void save(User user) {

        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        try {
            em.persist(user);
        }catch (Exception e){
            user = new User();
            user.setId(-1);
        }

        em.getTransaction().commit();
    }

    public User update(User user){

        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        User user1 = em.find(User.class,user.getId());
        user1.setUsername(user.getUsername());
        user1.setPassword(user.getPassword());
        user1.setEmail(user.getEmail());
        user1.setBirthdate(user.getBirthdate());
        user1.setAddress(user.getAddress());

        em.getTransaction().commit();

        return user1;
    }


    public boolean remove(User user){
        EntityManager em = emf.createEntityManager();
        em.remove(user);
        em.getTransaction().commit();
        return true;
    }








}
