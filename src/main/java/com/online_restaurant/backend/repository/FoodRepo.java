package com.online_restaurant.backend.repository;

import com.online_restaurant.backend.model.Food;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class FoodRepo {


    @Autowired
    private EntityManagerFactory emf;

    public boolean addFood(Food food){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(food);
        em.getTransaction().commit();
        return true;
    }


    public boolean updateFood(Food food){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Food food1 = em.find(Food.class,food.getId());
        food1.setName(food.getName());
        food1.setPrice(food.getPrice());
        food1.setDescription(food.getDescription());
        food1.setType(food.getType());
        food1.setStatus(food.getStatus());
        food1.setImagePath(food.getImagePath());
        em.getTransaction().commit();
        return true;
    }


    public boolean removeFood(Food food){

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        food = em.find(Food.class,food.getId());
        em.remove(food);
        em.getTransaction().commit();
        return true;
    }


    public List<Food> getAll(){

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Food> foodList = em.createQuery("select f from Food f ",Food.class).getResultList();
        em.getTransaction().commit();
        return foodList;
    }

    public Food get(Food food){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Food food1 = em.find(Food.class,food.getId());
        em.getTransaction().commit();

        return food1;

    }

}
